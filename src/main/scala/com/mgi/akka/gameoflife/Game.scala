package com.mgi.akka.gameoflife

import scala.swing._

object Game extends SimpleSwingApplication{

	val ROWS = 40
	val COLUMNS = 40
	
    var cellMatrix :CellMatrix = null	  
	var matrixDisplay :MatrixDisplay = null
	

	override def startup(args: Array[String]) {
  
      cellMatrix = CellMatrix( ROWS, COLUMNS )	
 
	  matrixDisplay = MatrixDisplay( cellMatrix )
	  
	  super.startup( args )
    }


	override def top = new MainFrame {

	  title = "Game of Life"
	  resizable = true
	
	  contents = new BoxPanel( Orientation.Vertical ){
	  
		contents += matrixDisplay.panel
		  
		contents += Button( "Run" ){ cellMatrix.run }
		contents += Button( "Pause" ){ cellMatrix.pause }
	  }
	}   
	
    override def shutdown() {		
	    cellMatrix.shutdown
	    matrixDisplay.shutdown
    }	
	
}