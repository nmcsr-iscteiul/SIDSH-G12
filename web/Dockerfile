FROM wordpress:latest

COPY . /var/www/html
WORKDIR /var/www/html

# Granting permissions to apache for plugin installation
RUN cd /var/www/html && \
    chown www-data:www-data -R *