package com.mgi.akka.gameoflife

import akka.actor._ 

object Events {

  sealed case class Event

  // runs the game (after init phase)
  case object Run extends Event

  // pauses the game (after game has started to run)
  case object Pause extends Event

  // initializes / resets a cell to be dead
  case object ResetDead extends Event
  
  // initializes / resets a cell to be alive
  case object ResetAlive extends Event
  
  // initializes / resets a cell with its neighbor cells (the cell depends on)
  case class ResetNeighbors( neighborCells :List[ActorRef] ) extends Event
  
  // adds a listener to a cell (for getting informed by state changes of a cell)
  case class AddListener( listener :ActorRef )
  
  // initializes a cell - forces a cell to initially calculate its state due to the initial state of its neighbors
  case object Init extends Event
  
  // asks a cell for its current neighbors which are alive 
  case class NeighborsAlive( count :Int ) extends Event
  
  // asks a cell for its current state
  case object GetState extends Event
  
  // asks a cell for its position within a cell matrix
  case object GetPosition extends Event
  
  // Answer to a request for a cells position
  case class Position( position :(Int,Int) )
  
  
  // common base class for cell state 
  abstract case class CellState extends Event{
	  def isAlive : Boolean
  }
  
  // informs a cell of the death of one of its neighbors
  case class Dead( cell :Cell ) extends CellState{
	  override def isAlive = false
  }
  
  // informs a cell of the (re)birth of one of its neighbors
  case class Alive( cell :Cell ) extends CellState{
	  override def isAlive = true
  }
  
  
  // asks a CellDisplay about its (Swing) panel (should go into a separate Event object for collecting the 'interface' of Actor 'CellDisplay'
  case object GetPanel extends Event
  
}