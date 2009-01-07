package org.jboss.webbeans.test.beans;

import javax.webbeans.Event;
import javax.webbeans.Fires;
import javax.webbeans.Initializer;

import org.jboss.webbeans.test.beans.StarFinch.Mess;

public class AuroraFinch
{

   private Mess someMess;

   @Initializer
   public AuroraFinch(@Fires Event<Mess> eventObject)
   {
      // Create a new mess and fire the event for it
      someMess = new Mess();
      eventObject.fire(someMess);
   }

   public Mess getSomeMess()
   {
      return someMess;
   }
}
