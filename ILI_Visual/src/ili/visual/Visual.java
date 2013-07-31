/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package ili.visual;


//import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import processing.core.*;

/**
 * This class is a virtual representation of a Visual object in Processing. it holds
 * the reference to the main applet (PApplet), contains a position and dimension and a name.
 * It simplifies the creation of objects as subclasses.
 * 
 * @example Hello 
 * 
 * (the tag @example followed by the name of an example included in folder 'examples' will
 * automatically include the example in the javadoc.)
 *
 */

public class Visual
{
  public static final int RECTANGULAR            =  1;
  public static final int CIRCULAR               =  2;

  private static final int PRE_PHASE              =  1;
  private static final int DRAW_PHASE             =  2;
  private static final int POST_PHASE             =  4;
  private static final int MOUSE                  =  1;
  private static final int KEYS                   =  2;

  public static final int REGISTER_PRE            =  1;
  public static final int REGISTER_DRAW           =  2;
  public static final int REGISTER_PRE_DRAW       =  3;
  public static final int REGISTER_POST           =  4;
  public static final int REGISTER_PRE_POST       =  5;
  public static final int REGISTER_DRAW_POST      =  6;
  public static final int REGISTER_PRE_DRAW_POST  =  7;

  public static final int REGISTER_MOUSE          =  1;
  public static final int REGISTER_KEY            =  2;
  public static final int REGISTER_MOUSE_KEY      =  3;
  
  public static final int MIDDLE				  =	1;
  public static final int TOPLEFT				  =	2;
  
  public static final String CLASS_TYPE			=	"Visual";
  public static final String UNNAMED			=	"Unnamed Visual";

  private PApplet _parent    =  null;
  private PVector _position  =  new PVector( 0, 0 );
  private PVector _dimension =  new PVector( 50, 50 );

  private int _registrations =  0;
  private int _interactions  =  0;
  private int _shape         =  Visual.RECTANGULAR;
  private int _origin		 =	Visual.MIDDLE;

  private String _name       =  Visual.UNNAMED;

  /**
   * This empty constructor allows you to create an object without reference to the main sketch.
   * Please be aware that you are unable to call registerXXX() functions before you set a parent reference.
   */
  public Visual() { }
  /**
	 * Constructor creates a new object at the specified PApplet (usually 'this' in Processing)
	 * 
	 * @example Hello
	 * @param theParent
	 */
  public Visual( PApplet parent)
  {
    this.setParent( parent );
  }
  /**
	 * Constructor creates a new object at the specified PApplet (usually 'this' in Processing)
	 * and the PVector as position 
	 * @example Hello
	 * @param theParent, position
	 */
  public Visual( PApplet parent, PVector position )
  {
    this.setParent( parent );
    this.setPosition( position );
  }
  /**
	 * Constructor creates a new object at the specified PApplet (usually 'this' in Processing)
	 * and the PVector as position and a second PVector as dimension
	 * @example Hello
	 * @param theParent, position, dimension
	 */
  public Visual( PApplet parent, PVector position, PVector dimension )
  {
    this.setParent( parent );
    this.setPosition( position );
    this.setDimension( dimension );
  }
  /**
   * Constructor creates a new object at the specified PApplet (usually 'this' in Processing).
   * Additionally, one needs to specify the position of the object, using x and y 
   * @param parent
   * @param x
   * @param y
   */
  public Visual( PApplet parent, float x, float y )
  {
    this.setParent( parent );
    this.setPosition( x, y );
  }
  /**
	 * Constructor creates a new object at the specified PApplet (usually 'this' in Processing)
	 * and the PVector as position and a second PVector as dimension
	 * @example Hello
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
  public Visual( PApplet parent, float x, float y, float w, float h )
  {
    this.setParent( parent );
    this.setPosition( x, y );
    this.setDimension( w, h );
  }

  /**
	 * Returns a reference to the main PApplet of this object.
	 * @return PApplet
	 */
  public PApplet getParent()
  {
    return this._parent;
  }
  /**
	 * Sets the reference to the main applet (PApplet, usually 'this' in Processing)
	 * 
	 * @param theParent
	 */
  public void setParent( PApplet parent )
  {
    this._parent  =  parent;
  }

  /**  Register to all phases, pre, post and draw  **/
  public void register()
  {
    this.register( Visual.REGISTER_PRE_DRAW_POST );
  }
  /**  Register to specific phases  **/
  public void register( int registerPhases )
  {
    if ( this.getParent() != null )
    {
      if ( (registerPhases & Visual.PRE_PHASE)  != 0 ) { 
        this.getParent().registerPre( this );
      }
      if ( (registerPhases & Visual.DRAW_PHASE) != 0 ) { 
        this.getParent().registerDraw( this );
      }
      if ( (registerPhases & Visual.POST_PHASE) != 0 ) { 
        this.getParent().registerPost( this );
      }

      this._registrations  =  ( this._registrations | registerPhases );
    }
  }
  /**  Standard deregistration which unregisters from all phases, pre, post, and draw  **/
  public void unregister(  )
  {
    this.unregister( this._registrations );
  }
  
  /**
	 * Defines for which of the phases (pre(), draw(), post()) this object unregisters itself. The
	 * other phases will still be called. Use Visual.REGISTER_XXX also to select from which phases 
	 * you wish to unregister.
	 * @param phases
	 */
  public void unregister( int registerPhases )
  {
    if ( this.getParent() != null )
    {
      if ( (registerPhases & Visual.PRE_PHASE)  != 0 ) { 
        this.getParent().unregisterPre( this );
      }
      if ( (registerPhases & Visual.DRAW_PHASE) != 0 ) { 
        this.getParent().unregisterDraw( this );
      }
      if ( (registerPhases & Visual.POST_PHASE) != 0 ) { 
        this.getParent().unregisterPost( this );
      }
    }

    this._registrations  =  PApplet.constrain( this._registrations - registerPhases, 0, 8 );
  }
  /**  Register for interactions such as mouse and keyboard events. This function registers for both  **/
  public void registerInteractions( )
  {
    this.registerInteractions( Visual.REGISTER_MOUSE_KEY );
  }
  /**  Register for specific interactions such as mouse and keyboard events   **/
  public void registerInteractions( int registerInteraction )
  {
    if ( this.getParent() != null )
    {
      if ( (registerInteraction & Visual.MOUSE) != 0 ) 
      { 
        this.getParent().registerMouseEvent( this );
      }
      if ( (registerInteraction & Visual.KEYS ) != 0 ) 
      { 
        this.getParent().registerKeyEvent( this );
      }
    }

    this._interactions  =  ( this._interactions | registerInteraction );
  }
  /**  Unregisters all existing interactions   **/
  public void unregisterInteractions( )
  {
    this.unregisterInteractions( this._interactions );
  }
  /**  Unregisters specific existing interactions   **/
  public void unregisterInteractions( int registerInteraction )
  {
    if ( this.getParent() != null )
    {
      if ( (registerInteraction & Visual.MOUSE) != 0 ) { 
        this.getParent().unregisterMouseEvent( this );
      }
      if ( (registerInteraction & Visual.KEYS ) != 0 ) { 
        this.getParent().unregisterKeyEvent( this );
      }
    }

    this._interactions  =  PApplet.constrain( this._interactions - registerInteraction, 0, 8 );
  }
  
  /**  Brings the complete object to the front by unregistering it from the main draw and re-registering it.
      This apparantly brings it to the front. Presumably the main sketch uses an ArrayList to register
      through all the subscribed elements and this simply places it on top of that list.  **/
  public void bringToFront()
  {
    int reRegister  =  this._interactions;
    this.unregister();
    this.register( reRegister );
  }

  /** OVERWRITE THIS IN YOUR OBJECT IF YOU WANT THIS PHASE TO BE CALLED.
   * Create a function called 'public void pre()' in your own Object.
   * pre() is called before draw() and can contain visual elements
   **/
  public void pre() { }                       //These have to be overwritten by the user!
  /** OVERWRITE THIS IN YOUR OBJECT IF YOU WANT THIS PHASE TO BE CALLED.
   * Create a function called 'public void pre()' in your own Object
   **/
  public void draw() { }                      //These have to be overwritten by the user!
  /** OVERWRITE THIS IN YOUR OBJECT IF YOU WANT THIS PHASE TO BE CALLED.
   * Create a function called 'public void post()' in your own Object
   **/
  public void post() { }                      //These have to be overwritten by the user!
  
  public void keyEvent( KeyEvent event ) { }      //These have to be overwritten by the user!
  
  /**
   * Do not call this function! This function is called externally by the main sketch of Processing.
   * Internally, this functions decides will call the following functions in your own sketch:
   * pressed(), pressedOutside(), doubleClicked(), doubleClickedOutside(), 
   * released(), releasedOutside(), over(), dragged()
   * @param event
   */
  public void mouseEvent( MouseEvent event )
  {
    boolean onVisual  =   this.hitTest( this.getParent().mouseX, this.getParent().mouseY );
    
    switch( event.getID() )
    {
      case MouseEvent.MOUSE_PRESSED:
        if( onVisual )
        {
          pressed();
        }
        else
        {
          pressedOutside();
        }
      break;
      case MouseEvent.MOUSE_CLICKED:
        if ( event.getClickCount() % 2 == 0 )
        {
          if( onVisual )
          {
            doubleClicked();
            
          }
          else
          {
            doubleClickedOutside();
          }
        }
        else
        {
          if( onVisual )
          {
            clicked();
          }
          else
          {
            clickedOutside();
          }
        }
        break;
      case MouseEvent.MOUSE_RELEASED:
        if( onVisual )
        {
          released();
        }
        else
        {
          releasedOutside();
        }        
        break;
      case MouseEvent.MOUSE_MOVED:
        if( onVisual ) over();
        break;
      case MouseEvent.MOUSE_DRAGGED:
        if( onVisual) dragged();
        break;
    }
  }

  
  /**
	 * Returns whether the x and y position specified are inside this visual, depending on the shape of this object
	 * 
	 * @return boolean
	 */
  public boolean hitTest( float x, float y )
  {
    return this.hitTest( new PVector( x, y) );
  }
  /**
	 * Returns whether the specified PVector is inside this visual, depending on the shape of this object
	 * 
	 * @return boolean
	 */
  public boolean hitTest( PVector testPoint )
  {
    boolean test  =  false;
    if ( this.getShape() == Visual.RECTANGULAR )  //Here we assume a rectangular shape, where x and y represent the centerpoint
    {
		if( (testPoint.x >= this.getLeft() && testPoint.x <= this.getRight()) 
		&& 	(testPoint.y >= this.getTop() && testPoint.y <= this.getBottom()) )
		{
			test	=	true;
		}
    }
    else if ( this.getShape() == Visual.CIRCULAR )  //Here we assume a circular shape, thus equal width and height
    {
      if ( this.getPosition().dist( testPoint ) <= this.getRadius() )
      {
        test  =  true;
      }
    }
    return test;
  }

  /**	
   * This function is called when the mouse is moved over the Visual. Please note that if the mouse
   * is NOT moved, this function will not be called. Create a function 'protected void over()' in your
   * own object.
   */
  protected void over() { }                      //These have to be overwritten by the user!
  /**
   * This function is called when the mouse is pressed on the Visual. A mouse-click accounts for a
   * press-release combination. If you want something to happen when the user only puts the button down,
   * use this function. 
   */
  protected void pressed() { }                   //These have to be overwritten by the user!
  /**
   * The same functionality as pressed(), but this function is called when the mouse is pressed outside
   * of the Visual.
   */
  protected void dragged() { }                   //These have to be overwritten by the user!
  protected void pressedOutside() { }            //These have to be overwritten by the user!
  protected void clicked() { }                   //These have to be overwritten by the user!
  protected void clickedOutside() { }            //These have to be overwritten by the user!
  protected void doubleClicked() { }             //These have to be overwritten by the user!
  protected void doubleClickedOutside() { }      //These have to be overwritten by the user!
  protected void released() { }                  //These have to be overwritten by the user!
  protected void releasedOutside() { }           //These have to be overwritten by the user!


  /*  POSITIONING  */
  /**
   * Allows you to change the point of origin of this visual.
   * This is specifically useful for hitTesting. Currently you
   * can select between Visual.MIDDLE and Visual.TOPLEFT.
   * @param origin
   */
  public void setOrigin( int origin )
  {
	  if( origin >= Visual.MIDDLE && origin <= Visual.TOPLEFT )
	  {
		  this._origin	=	origin;
	  }
  }
  /**
   * Returns the current point of origin. Either Visual.MIDDLE
   * or Visual.TOPLEFT
   */
  public int getOrigin()
  {
	  return this._origin;
  }
  /**
   * Returns the position of the object as PVector. Please note that we use the position
   * as a centerpoint in hitTest calculations.
   * @return PVector
   */
  public PVector getPosition()
  {
    return this._position;
  }
  /**
   * Returns the horizontal coordinate (or x-coordinate) of this object's position. Please note
   * that we use the position as a centerpoint in hitTest calculations.
   * @return float
   */
  public float getX()
  {
    return this.getPosition().x;
  }
  /**
   * Returns the vertical coordinate (or y-coordinate) of this object's position. Please note
   * that we use the position as a centerpoint in hitTest calculations.
   * @return float
   */
  public float getY()
  {
    return this.getPosition().y;
  }
  /**
   * Sets the position of this object to the provided position as PVector.
   * @param position
   */
  public void setPosition( PVector position )
  {
    this._position  =  position;
  }
  /**
   * Sets the position of this object to the provided horizontal (x) and vertical (y) position
   * on the screen.
   * @param x
   * @param y
   */
  public void setPosition( float x, float y )
  {
    this.setPosition( new PVector( x, y ) );
  }
  /**
   * Updates only the horizontal position (x) of this object and keeps the vertical position (y).
   * @param x
   */
  public void setX( float x )
  {
    this._position  =  new PVector( x, this.getPosition().y );
  }
  /**
   * Updates only the vertical position (y) of this object and keeps the horizontal position (x).
   * @param y
   */
  public void setY( float y )
  {
    this._position  =  new PVector( this.getPosition().x, y );
  }

  /*  DIMENSIONING  */
  /**  Returns a PVector containing the width as the first element (x),
   and the height as the second element (y).
   Eventual depth is stored in the third element (z).  **/
  public PVector getDimension()
  {
    return this._dimension;
  }
  /**  Returns the width of the element  **/
  public float getWidth()
  {
    return this.getDimension().x;
  }
  /**
   * Returns the left side of the visual element. Please note that the x,y is always the center point.
   * @return float
   */
  public float getLeft()
  {
	  float left	=	this.getX()-this.getWidth()/2;
	  if( this.getOrigin() == Visual.TOPLEFT )
	  {
		  left	=	this.getX();
	  }
	  return left;
  }
  /**
   * Returns the right side of the visual element. Please note that the x,y is always the center point. 
   * @return
   */
  public float getRight()
  {
	  float right	=	 this.getX()+this.getWidth()/2;
	  if( this.getOrigin() == Visual.TOPLEFT )
	  {
		  right	=	this.getX()+this.getWidth();
	  }
	  return right;
  }
  /**
   * Returns the top side of the visual element. Please note that the x,y is always the center point.
   * @return
   */
  public float getTop()
  {
	  float top	=	 this.getY()-this.getHeight()/2;
	  if( this.getOrigin() == Visual.TOPLEFT )
	  {
		  top	=	this.getY();
	  }
	  return top;
  }
  /**
   * Returns the top side of the visual element. Please note that the x,y is always the center point.
   * @return
   */
  public float getBottom()
  {
	  float bottom	=	 this.getY()+this.getHeight()/2;
	  if( this.getOrigin() == Visual.TOPLEFT)
	  {
		  bottom	=	this.getY()+this.getHeight();
	  }
	  return bottom;
  }
  /**  Returns the diameter of the element, in this case similar to the width  **/
  public float getDiameter()
  {
    return this.getDimension().x;
  }
  /**  Returns the radius of the element, in this case similar to half the width  **/
  public float getRadius()
  {
    return this.getDimension().x/2;
  }
  /**  Returns the height of the element  **/
  public float getHeight()
  {
    return this.getDimension().y;
  }
  /**  Set the dimensions of this object to the new dimensions specified as PVector **/
  public void setDimension( PVector dimension )
  {
    this._dimension  =  dimension;
  }
  /**  Sets the dimensions of this object to the width and height specified  **/
  public void setDimension( float w, float h )
  {
    this.setDimension( new PVector( w, h ) );
  }
  /**  Sets the width of this object to the specified value  **/
  public void setWidth( float w )
  {
    this._dimension  =  new PVector( w, this.getDimension().y );
  }
  /**  Sets the height of this object to the specified value  **/
  public void setHeight( float h )
  {
    this._dimension  =  new PVector( this.getDimension().x, h );
  }
  /**  Sets the radius, similar to half the width, of this object to the specified value  **/
  public void setRadius( float r )
  {
    this._dimension  =  new PVector( r*2, this.getDimension().y );
  }
  /**  Sets the diameter of this object to the specified value  **/
  public void setDiameter( float d )
  {
    this._dimension  =  new PVector( d, this.getDimension().y );
  }

  /*  NAMING  */
  /**  Returns the name of this visual  **/
  public String getName()
  {
    return this._name;
  }
  /**  Changes the name of this visual  **/
  public void setName( String name )
  {
    if ( name != null && !name.equals("") ) this._name  =  name;
  }

  /*  SHAPE  */
  /**
   * Sets the approximate shape of the object to the specified shape description. The shape is used
   * in hitTest calculations to determine whether a point intersects with this object. Currently, you
   * can select between Visual.RECTANGULAR, Visual.CIRCULAR.
   * @param shapeDescription
   */
  public void setShape( int shapeDescription  )
  {
    this._shape  =  PApplet.constrain( shapeDescription, Visual.RECTANGULAR, Visual.CIRCULAR );
  }
  /**
   * Returns the shape description of the object. Current options are: Visual.RECTANGULAR, Visual.CIRCULAR.
   * @return int
   */
  public int getShape()
  {
    return this._shape;
  }


  /*  DEBUG OUTPUT  */
  protected void traceln( String message, int depth )
  {
    this.traceln( message );
  }
  protected void traceln( String _msg )
  {
    System.out.print("["+Visual.datetimestamp()+ " ("+PApplet.nf(this.getParent().millis(), 8)+")]\t" ); //Print the time and the amount of millis()
    System.out.print("["+Visual.CLASS_TYPE+": "+this.getName()+"]\t");              //Print the object type and the name of the object
//    if (this.getName().length() <= 9) System.out.print("\t");        //If it is a small name, add a tab
    System.out.println( _msg );                                      //Print the message
  }
  protected void trace( String _msg )
  {
    System.out.print(_msg);
  }
  
  /**
   * Prints an error message with a time stamp and name information prepended
   * @param String
   */
  protected void errorln( String errormsg )
  {
	  System.err.print("["+Visual.datetimestamp()+ " ("+PApplet.nf(this.getParent().millis(), 8)+")]\t" ); //Print the time and the amount of millis()
	  System.err.print("["+Visual.CLASS_TYPE+": "+this.getName()+"]\t");              //Print the object type and the name of the object
	  System.err.println( errormsg );
  }
  /**
   * Returns a stamp of the current date "XXXX-XX-XX" in the format year-month-day
   * @return String
   */
  public static String datestamp()
  {
	  return PApplet.nf(PApplet.year(), 4) +"-"+
			 PApplet.nf(PApplet.month(), 2) +"-"+
			 PApplet.nf(PApplet.day(), 2);
	  	
  }
  /**
   * Returns a datestamp of the current date in the format "XXXX separator XX separator XX"
   * @param separator
   * @return
   */
  public static String datestamp( String separator )
  {
	  return PApplet.nf(PApplet.year(), 4) +"-"+separator+
				 PApplet.nf(PApplet.month(), 2) +"-"+separator+
				 PApplet.nf(PApplet.day(), 2);
		  	
  }
  /**
   * Returns a timestamp of the current time: hours-minutes-seconds in the format "XX:XX:XX"
   * @return String
   */
  public static String timestamp()
  {
    return  PApplet.nf(PApplet.hour(), 2)+":"+
    		PApplet.nf(PApplet.minute(), 2)+":"+
    		PApplet.nf(PApplet.second(), 2);
  }
  /**
   * Returns a timestamp of the current time in the format "XX separator XX separator XX"
   * @param separator
   * @return
   */
  public static String timestamp( String separator )
  {
	  return  PApplet.nf(PApplet.hour(), 2)+""+separator+
	    		PApplet.nf(PApplet.minute(), 2)+""+separator+
	    		PApplet.nf(PApplet.second(), 2);
	  
  }
  /**
   * Returns a composed date and time stamp of the current date and time in the format
   * 'XXXX-XX-XX XX:XX:XX' with year-month-day hours:minutes:seconds
   * @return
   */
  public static String datetimestamp()
  {
	  return Visual.datestamp()+" "+Visual.timestamp(); 
  }
}

