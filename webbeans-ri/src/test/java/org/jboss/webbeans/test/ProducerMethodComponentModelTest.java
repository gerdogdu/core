package org.jboss.webbeans.test;

import static org.jboss.webbeans.test.util.Util.getEmptyAnnotatedItem;

import java.lang.reflect.Method;

import javax.webbeans.Current;
import javax.webbeans.Dependent;

import org.jboss.webbeans.introspector.SimpleAnnotatedMethod;
import org.jboss.webbeans.introspector.SimpleAnnotatedType;
import org.jboss.webbeans.model.bean.ProducerMethodBeanModel;
import org.jboss.webbeans.model.bean.SimpleBeanModel;
import org.jboss.webbeans.test.annotations.Tame;
import org.jboss.webbeans.test.components.Animal;
import org.jboss.webbeans.test.components.BlackWidow;
import org.jboss.webbeans.test.components.DaddyLongLegs;
import org.jboss.webbeans.test.components.DeadlyAnimal;
import org.jboss.webbeans.test.components.DeadlySpider;
import org.jboss.webbeans.test.components.LadybirdSpider;
import org.jboss.webbeans.test.components.Spider;
import org.jboss.webbeans.test.components.SpiderProducer;
import org.jboss.webbeans.test.components.Tarantula;
import org.jboss.webbeans.test.components.TrapdoorSpider;
import org.jboss.webbeans.test.components.broken.ComponentWithFinalProducerMethod;
import org.jboss.webbeans.test.components.broken.ComponentWithStaticProducerMethod;
import org.testng.annotations.Test;

public class ProducerMethodComponentModelTest extends AbstractTest
{
   
   @Test @SpecAssertion(section="3.3")
   public void testStaticMethod() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<ComponentWithStaticProducerMethod> componentModel = new SimpleBeanModel<ComponentWithStaticProducerMethod>(new SimpleAnnotatedType<ComponentWithStaticProducerMethod>(ComponentWithStaticProducerMethod.class), getEmptyAnnotatedItem(ComponentWithStaticProducerMethod.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = ComponentWithStaticProducerMethod.class.getMethod("getString");
      boolean exception = false;
      try
      {
         new ProducerMethodBeanModel<String>(new SimpleAnnotatedMethod<String>(method), manager);
      }
      catch (Exception e) 
      {
         exception = true;
      }
      assert exception;
   }
   
   @Test @SpecAssertion(section="3.3")
   public void testApiTypes() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("produceTarantula");
      ProducerMethodBeanModel<Tarantula> tarantulaModel = new ProducerMethodBeanModel<Tarantula>(new SimpleAnnotatedMethod<Tarantula>(method), manager);
      assert tarantulaModel.getApiTypes().contains(Tarantula.class);
      assert tarantulaModel.getApiTypes().contains(DeadlySpider.class);
      assert tarantulaModel.getApiTypes().contains(Spider.class);
      assert tarantulaModel.getApiTypes().contains(Animal.class);
      assert tarantulaModel.getApiTypes().contains(DeadlyAnimal.class);
      assert !tarantulaModel.getApiTypes().contains(Object.class);
   }
   
   @Test @SpecAssertion(section="3.3.1")
   public void testDefaultBindingType() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("produceTarantula");
      ProducerMethodBeanModel<Tarantula> tarantulaModel = new ProducerMethodBeanModel<Tarantula>(new SimpleAnnotatedMethod<Tarantula>(method), manager);
      assert tarantulaModel.getBindingTypes().size() == 1;
      assert tarantulaModel.getBindingTypes().iterator().next().annotationType().equals(Current.class);
   }
   
   @Test
   public void testBindingType() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("produceTameTarantula");
      ProducerMethodBeanModel<Tarantula> tarantulaModel = new ProducerMethodBeanModel<Tarantula>(new SimpleAnnotatedMethod<Tarantula>(method), manager);
      assert tarantulaModel.getBindingTypes().size() == 1;
      assert tarantulaModel.getBindingTypes().iterator().next().annotationType().equals(Tame.class);
   }
   
   @Test @SpecAssertion(section="3.3")
   public void testFinalMethod() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<ComponentWithFinalProducerMethod> componentModel = new SimpleBeanModel<ComponentWithFinalProducerMethod>(new SimpleAnnotatedType<ComponentWithFinalProducerMethod>(ComponentWithFinalProducerMethod.class), getEmptyAnnotatedItem(ComponentWithFinalProducerMethod.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = ComponentWithFinalProducerMethod.class.getMethod("getString");
      boolean exception = false;
      try
      {
         new ProducerMethodBeanModel<String>(new SimpleAnnotatedMethod<String>(method), manager);     
      }
      catch (Exception e) 
      {
         exception = true;
      }
      assert exception;
   }
   
   @Test @SpecAssertion(section="3.3")
   public void testFinalMethodWithDependentScope() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("produceTrapdoorSpider");
      ProducerMethodBeanModel<TrapdoorSpider> trapdoorSpiderModel = new ProducerMethodBeanModel<TrapdoorSpider>(new SimpleAnnotatedMethod<TrapdoorSpider>(method), manager);
      assert trapdoorSpiderModel.getScopeType().equals(Dependent.class);
   }
   
   @Test @SpecAssertion(section="3.3.6")
   public void testNamedMethod() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("produceBlackWidow");
      ProducerMethodBeanModel<BlackWidow> blackWidowSpiderModel = new ProducerMethodBeanModel<BlackWidow>(new SimpleAnnotatedMethod<BlackWidow>(method), manager);
      assert blackWidowSpiderModel.getName().equals("blackWidow");
   }
   
   @Test @SpecAssertion(section="3.3.6")
   public void testDefaultNamedMethod() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("produceDaddyLongLegs");
      ProducerMethodBeanModel<DaddyLongLegs> daddyLongLegsSpiderModel = new ProducerMethodBeanModel<DaddyLongLegs>(new SimpleAnnotatedMethod<DaddyLongLegs>(method), manager);
      assert daddyLongLegsSpiderModel.getName().equals("produceDaddyLongLegs");
   }
   
   @Test @SpecAssertion(section="3.3.6")
   public void testDefaultNamedJavaBeanMethod() throws SecurityException, NoSuchMethodException
   {
      SimpleBeanModel<SpiderProducer> componentModel = new SimpleBeanModel<SpiderProducer>(new SimpleAnnotatedType<SpiderProducer>(SpiderProducer.class), getEmptyAnnotatedItem(SpiderProducer.class), manager);
      manager.getModelManager().addBeanModel(componentModel);
      Method method = SpiderProducer.class.getMethod("getLadybirdSpider");
      ProducerMethodBeanModel<LadybirdSpider> ladybirdSpiderModel = new ProducerMethodBeanModel<LadybirdSpider>(new SimpleAnnotatedMethod<LadybirdSpider>(method), manager);
      assert ladybirdSpiderModel.getName().equals("ladybirdSpider");
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.4")
   public void testDisposalMethodNonStatic()
   {
      // TODO Placeholder
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.4")
   public void testDisposalMethodMethodDeclaredOnWebBeanImplementationClass()
   {
      // TODO Placeholder
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.4")
   public void testDisposalMethodBindingAnnotations()
   {
      // TODO Placeholder
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.4")
   public void testDisposalMethodDefaultBindingAnnotations()
   {
      // TODO Placeholder
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.4")
   public void testDisposalMethodDoesNotResolveToProducerMethod()
   {
      // TODO Placeholder
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.5")
   public void testDisposalMethodDeclaredOnEnabledComponent()
   {
      // TODO Placeholder
      // TODO Move this
      
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.4")
   public void testComponentCanDeclareMultipleDisposalMethods()
   {
      // TODO move this 
      // TODO Placeholder
      assert false;
   }
   
   @Test(groups="disposalMethod") @SpecAssertion(section="3.3.5")
   public void testProducerMethodHasNoMoreThanOneDisposalMethod()
   {
      // TODO move this 
      // TODO Placeholder
      assert false;
   }
   
   @Test @SpecAssertion(section="2.7.2")
   public void testSingleStereotype()
   {
	   assert false;
   }
   
   @Test @SpecAssertion(section="2.7.2")
   public void testStereotypeOnNonProducerMethod()
   {
	   assert false;
   }
}
