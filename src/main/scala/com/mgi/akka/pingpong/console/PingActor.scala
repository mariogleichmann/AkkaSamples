package com.mgi.akka.pingpong.console

import akka.actor._

class PingActor( ponger :ActorRef ) extends Actor{

	var pongs = 0;
	
	def receive = {
		
		case Start 		=> 	println( "################# starting ping pong ..." )
						 	ponger ! Ping
		
		case Pong 		=> 	println( "################# receiving pong ("+ pongs +") so far" )
							pongs += 1
							if( pongs < 15 ) ponger ! Ping else ponger ! Stop 
					 	
		case PongsCount	=>	println( "************  receiving question of pongs count ")
							self reply pongs
		
	}
}