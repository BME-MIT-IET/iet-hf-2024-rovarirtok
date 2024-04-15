FROM norbertkurcsi/webswing:24.1

RUN apt-get update && apt-get install --no-install-recommends -y \
    wget \
    unzip \
    xvfb \
    libxext6 \
    libxi6 \
    libxtst6 \
    libxrender1 \
    libpangoft2-1.0-0

RUN unzip /opt/webswing/dist.zip -d /opt/webswing && \
    mv /opt/webswing/webswing*/* /opt/webswing && \
    rm -d /opt/webswing/dist.zip /opt/webswing/webswing*/

COPY webswing/webswing.config /opt/webswing/webswing.config

RUN sed -i 's/http:\/\/localhost:8080\/admin/${webswing.admin.url}/' /opt/webswing/webswing.config

ENV WEBSWING_HOME=/opt/webswing \
    DISPLAY=:99 \
	WEBSWING_OPTS="-h 0.0.0.0 -j /opt/webswing/jetty.properties -serveradmin -pfa /opt/webswing/admin/webswing-admin.properties -adminctx /admin -aw admin/webswing-admin-server.war" \
	WEBSWING_JAVA_OPTS="-Xmx256M -Djava.net.preferIPv4Stack=true -Dwebswing.admin.url=http://localhost:8080/admin"

WORKDIR /opt/webswing

RUN mkdir -p /etc/service/xvfb && \
    echo "#!/bin/sh\nexec Xvfb :99" > /etc/service/xvfb/run && \
    chmod +x /etc/service/xvfb/run

RUN mkdir /etc/service/webswing && \
    echo "#!/bin/sh\ncd $WEBSWING_HOME\nexec $JAVA_HOME/bin/java \$WEBSWING_JAVA_OPTS -jar $WEBSWING_HOME/server/webswing-jetty-launcher.jar -w $WEBSWING_HOME/webswing-server.war \$WEBSWING_OPTS" > /etc/service/webswing/run && \
    chmod +x /etc/service/webswing/run

EXPOSE 8080

COPY target/DrukmakoriSivatag-1.0-SNAPSHOT.jar /opt/webswing/apps/DrukmakoriSivatag/DrukmakoriSivatag-1.0-SNAPSHOT.jar

COPY webswing/start.sh /opt/webswing/start.sh
RUN chmod +x /opt/webswing/start.sh
CMD ./start.sh