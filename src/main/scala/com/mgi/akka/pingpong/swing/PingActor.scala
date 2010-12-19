package com.mgi.akka.pingpong.swing

import akka.actor._
import scala.swing._

class PingActor( ponger :ActorRef, button :Button ) extends Actor{

	val MAX_PONGS = 99999
	
	var pongs = 0;
	
	def receive = {
		
		case Start 		=> 	println( "################# starting ping pong ..." )
						 	ponger ! Ping
		
		case Pong 		=> 	println( "################# receiving pong ("+ pongs +") so far" )
							pongs += 1
							button.text = pongs + " pongs"
							if( pongs < MAX_PONGS ) ponger ! Ping else ponger ! Stop 
					 	
		case PongsCount	=>	println( "************  receiving question of pongs count ")
							self reply pongs
		
	}
}