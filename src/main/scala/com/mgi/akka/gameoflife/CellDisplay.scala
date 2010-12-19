package com.mgi.akka.gameoflife

import scala.swing._
import scala.swing.event._
import javax.swing.border.LineBorder
import java.awt.Color
import akka.actor._ 
import Events._

class CellDisplay( cell :ActorRef ) extends Actor{

  val panel = new FlowPanel {
		  	
	  	    border = LineBorder.createBlackLineBorder
            background = Color.WHITE
            contents += new Label("")
		  			  	
		  	listenTo( mouse.clicks )
		  	
		  	reactions += {
		  	  
		  	  case e: MouseClicked => {              
		  	 	  
                val oldState :CellState = ( ( cell !! GetState ).get ).asInstanceOf[CellState]
               
                cell ! ( if( oldState.isAlive ) ResetDead else ResetAlive )
                
                background = if( oldState.isAlive ) Color.WHITE else Color.BLUE
		  	  }
		  	}
	      }	
	
  def receive = {
 	  
 	  case Dead( _ )  => 
 	   	  panel.background = Color.WHITE
 	  
 	  case Alive( _ ) => 
 	   	  panel.background = Color.BLUE
 	  
 	  case GetPanel => {
 	 	  self reply panel
 	  }
  }
  
  
}