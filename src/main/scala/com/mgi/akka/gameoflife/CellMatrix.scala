package com.mgi.akka.gameoflife

import akka.actor._ 
import akka.actor.Actor._
import Events._


class CellMatrix( cellRows : List[CellRow] ) extends Traversable[ActorRef] {

	def row( i :Int ) = cellRows( i )
	def apply( i: Int ) = row( i )
	
	def dimension = ( cellRows.size, cellRows(0).size )
		
	def run = {		
	  foreach( cell => cell !! Init )  
	  foreach( cell => cell ! Run )
	}
	
	def pause = foreach( cell => cell ! Pause )
	
	def shutdown = foreach( cell => cell.stop )
	
	override def foreach[U]( f: ActorRef => U ) {		
		cellRows.foreach( row => row.foreach( cell => f( cell ) ) )
	}	
}

object CellMatrix{
	
	def apply(  rows :Int, columns :Int ) : CellMatrix = {		
		
		val cells = new CellMatrix( makeCellRows( rows, columns ) )
		
		cells.foreach( _.start )
		
		interconnect( cells )
		
		cells
	}
	
	def makeCellRows( rows :Int, columns :Int ) : List[CellRow] = {		
		( for( rowNum <- 0 to rows - 1 ) yield CellRow( rowNum, columns ) ) toList
	}	
	
	def interconnect( cellMatrix :CellMatrix ){		
		cellMatrix.foreach( cell => connect( cell, cellMatrix ) )
	}
	
	def connect( cell :ActorRef, cellMatrix :CellMatrix ){
		
		val( rowDimension, columnDimension ) = cellMatrix.dimension
		
		val Position( (row, column) ) = ( ( cell !! GetPosition ).get ).asInstanceOf[Position]
		
		var neighbours :List[ActorRef] = Nil
		
		neighbours = cellMatrix( row )( (column + columnDimension - 1) % columnDimension ) :: neighbours // left neighbour
		neighbours = cellMatrix( row )( (column + 1) % columnDimension ) :: neighbours // right neighbour

		neighbours = cellMatrix( (row + rowDimension - 1) % rowDimension )( (column + columnDimension - 1) % columnDimension ) :: neighbours // above left neighbour
		neighbours = cellMatrix( (row + rowDimension - 1) % rowDimension )( column ) :: neighbours // above neighbour
		neighbours = cellMatrix( (row + rowDimension - 1) % rowDimension )( (column +1 )% columnDimension ) :: neighbours // above right neighbour
		
		neighbours = cellMatrix( (row + 1) % rowDimension )( (column + columnDimension - 1)% columnDimension ) :: neighbours // below left neighbour
		neighbours = cellMatrix( (row + 1) % rowDimension )( column ) :: neighbours // below neighbour
		neighbours = cellMatrix( (row + 1) % rowDimension )( (column + 1) % columnDimension ) :: neighbours // below right neighbour
		
		cell ! ResetNeighbors( neighbours )
	}
}


class CellRow( cells : List[ActorRef] ) extends Traversable[ActorRef]{
	
	def cell( i :Int ) : ActorRef = cells( i )
	def apply( i :Int ) = cell( i )
	
	def start = foreach( _.start )
	
	override def foreach[U]( f: ActorRef => U ) {		
		cells.foreach( cell => f( cell ) )
	}
}

object CellRow{
	
	def apply( row :Int, columns :Int ) : CellRow = {	
		new CellRow( ( for ( column <- 0 to columns - 1 ) yield actorOf( new Cell( row, column ) ) ) toList )		
	}
}
