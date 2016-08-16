javac Convert.java Cart.java
echo Main-Class: Cart > MANIFEST.MF
jar -cvmf MANIFEST.MF Cart.jar Convert.class Cart.class
cat stub.sh Cart.jar > Cart.run && chmod +x Cart.run 
