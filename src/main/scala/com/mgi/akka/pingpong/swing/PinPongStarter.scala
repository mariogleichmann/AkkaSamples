package com.mgi.akka.pingpong.swing

import akka.actor._
import akka.actor.Actor._
import scala.swing._

object PinPongStarter extends SimpleSwingApplication{

  val pingButton = new Button( "ping" )
  val pongButton = new Button( "pong" )
  
  val ponger :ActorRef = actorOf( new PongActor( pongButton  ) )		
  val pinger = actorOf( new PingActor( ponger, pingButton ) )
	
	
  override def startup(args: Array[String]) {
	  
    println( "starting..." )
	  
    super.startup( args )
    
	pinger.start
	ponger.start	
	
	//pinger ! Start
  }
  
  
  override def shutdown() {
		
	pinger.stop
	ponger.stop	  
  }
	
	
	
  override def top = new MainFrame {
    
	title = "Ping Pong"
	resizable = true
	
	contents = new GridPanel( 1,2 ) {
	
      contents += pingButton
      contents += pongButton 
      contents += Button( "start" ){ pinger ! Start }
    }
  }
}