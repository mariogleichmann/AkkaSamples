package com.mgi.akka.pingpong.swing

sealed case class Event

case class Ping( ping :String ) extends Event

case class Pong( pong :String ) extends Event

case class Start extends Event

case class Stop extends Event

case class PongsCount extends Event