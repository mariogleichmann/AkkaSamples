package com.mgi.akka.pingpong.swing

import akka.actor._ 
import scala.swing._

class PongActor( button :Button ) extends Actor{

	var repong = true
	
	var pingsReceived = 0
	
	def receive = {
		
		case Stop			=>  println( " ................ stop received ... stopping to repong" )
								repong = false
		
		case Ping if repong => 	println( "................. ping received" )
								pingsReceived += 1
								button.text = if( pingsReceived % 2 == 0 ) "x" else "o"
								self reply Pong
	}
}