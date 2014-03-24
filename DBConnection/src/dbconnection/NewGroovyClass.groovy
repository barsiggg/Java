/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbconnection
/**
 *
 * @author Администратор
 */
class NewGroovyClass {

   def greeting = "Hello from Groovy"
   public getMetod(){
       MyConnection test= new MyConnection()
       println test.getText()
       println greeting
   }
   
        
}

