package com.mgi.akka.gameoflife

import akka.actor._ 
import akka.actor.Actor._
import scala.swing._
import Events._

class MatrixDisplay( cellDisplays : Traversable[ActorRef], dimension :(Int,Int) ) {

	val (rows, columns) = dimension
	
	lazy val panel = new GridPanel( rows, columns ) {
	  cellDisplays.foreach( cellDisplay => contents += ( (cellDisplay !! GetPanel).get ).asInstanceOf[FlowPanel] )
	}
	
	def shutdown = cellDisplays.foreach( display => display.stop() )
}

object MatrixDisplay{
	
	def apply( cells :CellMatrix ) = {		
		new MatrixDisplay( displaysFor( cells ), cells.dimension )
	}
	
	def displaysFor( cellMatrix : CellMatrix ) : Traversable[ActorRef] = {		
		cellMatrix.map( cell => displayFor( cell ) )
	}
	
	def displayFor( cell :ActorRef ) : ActorRef = {
		
		val display = actorOf( new CellDisplay( cell ) ).start
		
		cell ! AddListener( display )
		
		display
	}
}