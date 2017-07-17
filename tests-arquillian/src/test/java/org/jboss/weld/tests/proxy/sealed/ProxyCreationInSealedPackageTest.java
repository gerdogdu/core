/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.tests.proxy.sealed;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.BeanArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.weld.test.util.Utils;
import org.jboss.weld.tests.category.Integration;
import org.jboss.weld.tests.proxy.sealed.library.SealedBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 *
 * @author <a href="mailto:manovotn@redhat.com">Matej Novotny</a>
 */
@RunWith(Arquillian.class)
@Category(Integration.class)
public class ProxyCreationInSealedPackageTest {

    @Deployment
    public static WebArchive deploy() {

        // JAR contains SealedBean class in sealed package
        JavaArchive jar = ShrinkWrap.create(BeanArchive.class, "sealed.jar").addPackage(SealedBean.class.getPackage())
            .addAsManifestResource(new StringAsset("Manifest-Version: 1.0\n"
                    + "\n"
                    + "Name: org/jboss/weld/tests/proxy/sealed/library/\n"
                    + "Sealed: true\n"), "MANIFEST.MF");

        // WAR contains this test class and ordinary bean class
        return ShrinkWrap.create(WebArchive.class, Utils.getDeploymentNameAsHash(ProxyCreationInSealedPackageTest.class, Utils.ARCHIVE_TYPE.WAR))
            .addPackage(ProxyCreationInSealedPackageTest.class.getPackage())
            .addAsLibraries(jar)
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    NotSealedBean bean;

    @Test
    public void testProxyCreationWithSealedJar() {
        // assert that the package is sealed
        Assert.assertTrue(SealedBean.class.getPackage().isSealed());
        // invoke any method to make sure we create proxy
        bean.ping();
    }
}
