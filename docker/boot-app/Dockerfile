FROM java:8
MAINTAINER Gautam Pachnanda <gpachnanda@gmail.com>

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

VOLUME /tmp
ADD setup.sh /opt/setup.sh
RUN chmod +x /opt/setup.sh
ENTRYPOINT ["/opt/setup.sh"]