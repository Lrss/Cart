javac Convert.java Cart.java
echo Main-Class: Cart > MANIFEST.MF
jar -cvmf MANIFEST.MF Cart.jar Convert.class Cart.class
echo '#!/bin/sh
MYSELF=`which "$0" 2>/dev/null`
[ $? -gt 0 -a -f "$0" ] && MYSELF="./$0"
java=java
if test -n "$JAVA_HOME"; then
    java="$JAVA_HOME/bin/java"
fi
exec "$java" $java_args -jar $MYSELF "$@"
exit 1' > stub.sh
#chmod +x stub.sh
cat stub.sh Cart.jar > cart && chmod +x cart
rm *.class
rm *.jar
rm *.MF
rm stub.sh
