FROM stilliard/pure-ftpd:hardened 

RUN mkdir /home/node_modules/ && \
    mkdir /home/resource/ && \
    mkdir /node/

COPY node_src/app.js /home/
COPY node_src/package.json /home/
COPY node_src/package-lock.json /home/
COPY node_src/node_modules/ /home/node_modules/
COPY node-v8.11.3-linux-x64/ /node/

ENV NODE_HOME=/node
ENV PATH=$NODE_HOME/bin:$PATH

WORKDIR /home

RUN useradd -m -d /home/ftp4node -s /bin/bash -G ftpgroup ftp4node && \
    /bin/bash -c "echo -e 'q1w2e3r4\nq1w2e3r4' | passwd ftp4node"

ENTRYPOINT service pure-ftpd start && npm run start
