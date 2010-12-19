package com.mgi.akka.pingpong.console

import akka.actor._ 

class PongActor extends Actor{

	var repong = true
	
	def receive = {
		
		case Stop			=>  println( " ................ stop received ... stopping to repong" )
								repong = false
		
		case Ping if repong => 	println( "................. ping received" )
								self reply Pong
	}
}