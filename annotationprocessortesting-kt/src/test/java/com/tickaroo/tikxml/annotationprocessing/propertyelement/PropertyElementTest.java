/*
 * Copyright (C) 2015 Hannes Dorfmann
 * Copyright (C) 2015 Tickaroo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tickaroo.tikxml.annotationprocessing.propertyelement;

import com.tickaroo.tikxml.TestUtils;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.annotationprocessing.DateConverter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import okio.Buffer;
import org.junit.*;

/**
 * @author Hannes Dorfmann
 */
public class PropertyElementTest {

  @Test
  public void fieldAccess() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    PropertyItem item = xml.read(TestUtils.sourceForFile("property_item.xml"), PropertyItem.class);

    Date date = DateConverter.Companion.getFormat().parse("1988-03-04");

    Assert.assertEquals("foo", item.aString);
    Assert.assertEquals(123, item.anInt);
    Assert.assertEquals(true, item.aBoolean);
    Assert.assertEquals(23.42, item.aDouble, 0);
    Assert.assertEquals(2147483648L, item.aLong);
    Assert.assertEquals(date, item.aDate);

    Assert.assertEquals(123, (int) item.intWrapper);
    Assert.assertEquals(true, item.booleanWrapper);
    Assert.assertEquals(23.42, item.doubleWrapper, 0);
    Assert.assertEquals(2147483648L, (long) item.longWrapper);

    // Write XML
    // Writing tests
    Buffer buffer = new Buffer();
    xml.write(buffer, item);

    String xmlStr =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><aString>foo</aString><anInt>123</anInt><aBoolean>true</aBoolean><aDouble>23.42</aDouble><aLong>2147483648</aLong><aDate>1988-03-04</aDate><intWrapper>123</intWrapper><booleanWrapper>true</booleanWrapper><doubleWrapper>23.42</doubleWrapper><longWrapper>2147483648</longWrapper></item>";
    Assert.assertEquals(xmlStr, TestUtils.bufferToString(buffer));

    PropertyItem item2 = xml.read(TestUtils.sourceFrom(xmlStr), PropertyItem.class);
    Assert.assertEquals(item, item2);
  }

  @Test
  public void settersGetters() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    PropertyItemWithGetterSetters item =
        xml.read(TestUtils.sourceForFile("property_item.xml"), PropertyItemWithGetterSetters.class);

    Date date = DateConverter.Companion.getFormat().parse("1988-03-04");

    Assert.assertEquals("foo", item.getAString());
    Assert.assertEquals(123, item.getAnInt());
    Assert.assertEquals(true, item.getABoolean());
    Assert.assertEquals(23.42, item.getADouble(), 0);
    Assert.assertEquals(2147483648L, item.getALong());
    Assert.assertEquals(date, item.getADate());

    Assert.assertEquals(123, (int) item.getIntWrapper());
    Assert.assertEquals(true, item.getBooleanWrapper());
    Assert.assertEquals(23.42, item.getDoubleWrapper(), 0);
    Assert.assertEquals(2147483648L, (long) item.getLongWrapper());

    // Write XML
    // Writing tests
    Buffer buffer = new Buffer();
    xml.write(buffer, item);

    String xmlStr =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><aString>foo</aString><anInt>123</anInt><aBoolean>true</aBoolean><aDouble>23.42</aDouble><aLong>2147483648</aLong><aDate>1988-03-04</aDate><intWrapper>123</intWrapper><booleanWrapper>true</booleanWrapper><doubleWrapper>23.42</doubleWrapper><longWrapper>2147483648</longWrapper></item>";
    Assert.assertEquals(xmlStr, TestUtils.bufferToString(buffer));

    PropertyItemWithGetterSetters item2 =
        xml.read(TestUtils.sourceFrom(xmlStr), PropertyItemWithGetterSetters.class);
    Assert.assertEquals(item, item2);
  }

  @Test
  public void skipAttributes() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(false).build();

    PropertyItemWithGetterSetters item =
        xml.read(TestUtils.sourceForFile("property_item_with_attributes.xml"),
            PropertyItemWithGetterSetters.class);

    Assert.assertEquals("foo", item.getAString());

    // Write XML
    // Writing tests
    Buffer buffer = new Buffer();
    xml.write(buffer, item);

    String xmlStr =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><aString>foo</aString><anInt>0</anInt><aBoolean>false</aBoolean><aDouble>0.0</aDouble><aLong>0</aLong></item>";
    Assert.assertEquals(xmlStr, TestUtils.bufferToString(buffer));

    PropertyItemWithGetterSetters item2 =
        xml.read(TestUtils.sourceFrom(xmlStr), PropertyItemWithGetterSetters.class);
    Assert.assertEquals(item, item2);
  }

  @Test
  public void failSkippingAttributes() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    try {

      PropertyItemWithGetterSetters item =
          xml.read(TestUtils.sourceForFile("property_item_with_attributes.xml"),
              PropertyItemWithGetterSetters.class);
      Assert.fail("Exception expected");
    } catch (IOException e) {
      Assert.assertEquals("Unread attribute 'a' at path /item/aString[@a]", e.getMessage());
    }
  }





  @Test
  public void fieldAccessDataCall() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    PropertyItemDataClass item = xml.read(TestUtils.sourceForFile("property_item.xml"), PropertyItemDataClass.class);

    Date date = DateConverter.Companion.getFormat().parse("1988-03-04");

    Assert.assertEquals("foo", item.aString);
    Assert.assertEquals(123, item.anInt);
    Assert.assertEquals(true, item.aBoolean);
    Assert.assertEquals(23.42, item.aDouble, 0);
    Assert.assertEquals(2147483648L, item.aLong);
    Assert.assertEquals(date, item.aDate);

    Assert.assertEquals(123, (int) item.intWrapper);
    Assert.assertEquals(true, item.booleanWrapper);
    Assert.assertEquals(23.42, item.doubleWrapper, 0);
    Assert.assertEquals(2147483648L, (long) item.longWrapper);

    // Write XML
    // Writing tests
    Buffer buffer = new Buffer();
    xml.write(buffer, item);

    String xmlStr =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><aString>foo</aString><anInt>123</anInt><aBoolean>true</aBoolean><aDouble>23.42</aDouble><aLong>2147483648</aLong><aDate>1988-03-04</aDate><intWrapper>123</intWrapper><booleanWrapper>true</booleanWrapper><doubleWrapper>23.42</doubleWrapper><longWrapper>2147483648</longWrapper></item>";
    Assert.assertEquals(xmlStr, TestUtils.bufferToString(buffer));

    PropertyItemDataClass item2 = xml.read(TestUtils.sourceFrom(xmlStr), PropertyItemDataClass.class);
    Assert.assertEquals(item, item2);
  }

  @Test
  public void settersGettersDataClass() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    PropertyItemWithGetterSettersDataClass item =
            xml.read(TestUtils.sourceForFile("property_item.xml"), PropertyItemWithGetterSettersDataClass.class);

    Date date = DateConverter.Companion.getFormat().parse("1988-03-04");

    Assert.assertEquals("foo", item.getAString());
    Assert.assertEquals(123, item.getAnInt());
    Assert.assertEquals(true, item.getABoolean());
    Assert.assertEquals(23.42, item.getADouble(), 0);
    Assert.assertEquals(2147483648L, item.getALong());
    Assert.assertEquals(date, item.getADate());

    Assert.assertEquals(123, (int) item.getIntWrapper());
    Assert.assertEquals(true, item.getBooleanWrapper());
    Assert.assertEquals(23.42, item.getDoubleWrapper(), 0);
    Assert.assertEquals(2147483648L, (long) item.getLongWrapper());

    // Write XML
    // Writing tests
    Buffer buffer = new Buffer();
    xml.write(buffer, item);

    String xmlStr =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><aString>foo</aString><anInt>123</anInt><aBoolean>true</aBoolean><aDouble>23.42</aDouble><aLong>2147483648</aLong><aDate>1988-03-04</aDate><intWrapper>123</intWrapper><booleanWrapper>true</booleanWrapper><doubleWrapper>23.42</doubleWrapper><longWrapper>2147483648</longWrapper></item>";
    Assert.assertEquals(xmlStr, TestUtils.bufferToString(buffer));

    PropertyItemWithGetterSettersDataClass item2 =
            xml.read(TestUtils.sourceFrom(xmlStr), PropertyItemWithGetterSettersDataClass.class);
    Assert.assertEquals(item, item2);
  }

  @Test
  public void skipAttributesDataClass() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(false).build();

    PropertyItemWithGetterSettersDataClass item =
            xml.read(TestUtils.sourceForFile("property_item_with_attributes.xml"),
                    PropertyItemWithGetterSettersDataClass.class);

    Assert.assertEquals("foo", item.getAString());

    // Write XML
    // Writing tests
    Buffer buffer = new Buffer();
    xml.write(buffer, item);

    String xmlStr =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><aString>foo</aString><anInt>0</anInt><aBoolean>false</aBoolean><aDouble>0.0</aDouble><aLong>0</aLong></item>";
    Assert.assertEquals(xmlStr, TestUtils.bufferToString(buffer));

    PropertyItemWithGetterSettersDataClass item2 =
            xml.read(TestUtils.sourceFrom(xmlStr), PropertyItemWithGetterSettersDataClass.class);
    Assert.assertEquals(item, item2);
  }

  @Test
  public void failSkippingAttributesDataClass() throws IOException, ParseException {
    TikXml xml = new TikXml.Builder().exceptionOnUnreadXml(true).build();

    try {

      PropertyItemWithGetterSettersDataClass item =
              xml.read(TestUtils.sourceForFile("property_item_with_attributes.xml"),
                      PropertyItemWithGetterSettersDataClass.class);
      Assert.fail("Exception expected");
    } catch (IOException e) {
      Assert.assertEquals("Unread attribute 'a' at path /item/aString[@a]", e.getMessage());
    }
  }
}
