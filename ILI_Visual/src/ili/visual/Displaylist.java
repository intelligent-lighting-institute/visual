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

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import processing.core.*;

public class Displaylist
{
	private PApplet _parent;
	private ArrayList<Visual> _displaylist	=	new ArrayList<Visual>();
	
	public Displaylist( PApplet parent )
	{
		this.setParent( parent );
	}
	
	public void pre(){}
	public void draw(){}
	public void post(){}
	
	public PApplet setParent( PApplet parent )
	{
		return this._parent	=	parent;
	}
	public PApplet getParent()
	{
		return this._parent;
	}
	
	//public void addVisual( Visual visual )
	//public void removeVisual( Visual visual )
	//public void getVisual( Visual visual )
	//public void getVisualByName( Visual visual )
	//public void bringToFront( Visual visual )
	//public void sendToBack( Visual visual )
	//public void swap( Visual visuone, Visual visutwo )
}