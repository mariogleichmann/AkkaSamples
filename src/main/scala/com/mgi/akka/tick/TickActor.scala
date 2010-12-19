package com.mgi.akka.tick

import akka.actor._

case object Tick

class TickActor extends Actor{
  
  self.id = "service:ticker"
	
  var counter = 0
  
  def receive = {
    
    case Tick => 
      		counter += 1
      		println( "ticked ########################## " + counter )
      		
      		for( i <- 1 to 99999 ){
      			for( j <- 1 to 9999 ){}
      		}
      		println( "ende ticked ......................................... " + counter )
  }
  
  
  override def preStart = println( "+++++++++++++++before start" )
  
  override def postStop = println( "+++++++++++++++after stop" )

}