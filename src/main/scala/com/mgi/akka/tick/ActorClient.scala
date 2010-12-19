package com.mgi.akka.tick

import akka.actor._
import akka.actor.Actor._

object ActorClient {

	def main( arg :Array[String]){
		
		
		println( "..................Hello Akka" )
		
		val counter :ActorRef = actorOf[TickActor]
		
		// here we could pass constructor arguments to the actor (if there are some)
		val anotherCounter = actorOf( new TickActor() )
		
		print( "......................starting counter " + counter + "..." )
		
		counter.start
		anotherCounter.start
		
		println( "[..................started]" )
		
		// asynchronous message sending (fire and forget)
		println( "tick 1..." )
		counter ! Tick
		
		// synchronous (blocking) message sending
		//anotherCounter !! Tick
		
		println( "tick 2..." )
		counter ! Tick
		
		println( "tick 3..." )
		counter ! Tick
		
		println( "tick 4..." )
		counter ! Tick
		
  		for( i <- 1 to 99999 ){
  			for( j <- 1 to 99999 ){}
  		}
		
		print( ".........................stopping counter...")
		
		counter.stop()
		anotherCounter.stop()
		
		println( "[..................stoped]" )
	}
}