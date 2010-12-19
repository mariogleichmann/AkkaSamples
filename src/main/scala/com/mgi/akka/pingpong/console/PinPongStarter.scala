package com.mgi.akka.pingpong.console

import akka.actor._
import akka.actor.Actor._

object PinPongStarter {

	def main( args :Array[String]) = {
		
		val ponger :ActorRef = actorOf[PongActor]		
		val pinger = actorOf( new PingActor( ponger ) )
		
		pinger.start
		ponger.start
		
		pinger ! Start
		
		
		
		var pongsCount = 0
		while( pongsCount < 999999999 ) pongsCount += 1		
		
//		while( pongsCount < 10 ) {
//			spawn{
//				pongsCount = ( pinger !! PongsCount ).asInstanceOf[Int]
//			}
//		}
		
		
		pinger.stop
		ponger.stop
		
	}
}