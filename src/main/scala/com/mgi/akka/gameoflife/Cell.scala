package com.mgi.akka.gameoflife

import akka.actor._ 
import Events._

class Cell( val position :(Int,Int) ) extends Actor {
	
  var state :CellState = Dead( this )
  
  var neighborCells :List[ActorRef] = Nil
  
  var neighborCellsAlive = 0;
  
  var listeners :List[ActorRef] = Nil

  def initializing :Receive = {
	  
	  case ResetDead => 
	     state = Dead( this )
	  
	  case ResetAlive => 
	      state = Alive( this )
	  
	  case ResetNeighbors( cells ) => 
	     neighborCells = cells
	     
	  case AddListener( listener ) =>
	     listeners = listener :: listeners
	     
	  case GetPosition =>
	     self reply Position( position )
	  
	  case Init => {
	 	  init
	 	  self reply NeighborsAlive( neighborCellsAlive )
	  }	  
	   					   
	  case GetState => {
	 	  self reply state
	  }
	  
	  case Run => {
	 	 if( newState ) fireNewState 
	 	 become( running )
	  }	  
	   
	  case _ => println( "sorry ... not in mode initializing" )
  }
  
  def running :Receive = {

	  case Dead( cell )	=> {
	 	  println( "neighbor gone" )
	 	  if( neighborCellsAlive > 0 ) neighborCellsAlive -= 1
	 	  if( newState ) fireNewState 
	  }
	   
	  case Alive( cell ) => { 
	 	   println( "neighbor born" )
	 	  neighborCellsAlive += 1
	 	  if( newState ) fireNewState 
	  }
	   
	  case Pause =>
	      become( initializing )
  }
  

  def receive = initializing
  


  def init{  

    neighborCells.foreach{ cell =>

      val neighborState :CellState = ( ( cell !! GetState ).get ).asInstanceOf[CellState]

	  neighborCellsAlive = if( neighborState.isAlive ) neighborCellsAlive + 1 else neighborCellsAlive
    }
  }
  
  def newState() : Boolean = {
	  
	  val oldState = state

	  state = if( neighborCellsAlive >= 3 && neighborCellsAlive <= 5 ) Alive( this ) else Dead( this )

	  println( "state of cell " + position + " : " + oldState + " -> " + state )
	   
	  !( oldState.isAlive == state.isAlive )
  }
  
  
  def fireNewState {
	   
	  listeners.foreach( _ ! state )
	  neighborCells.foreach( _ ! state )
  }
  
  
  override def toString = "Cell" + position
  
}