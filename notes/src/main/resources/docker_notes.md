### DOCKER notes
Цикл статей по докеру:
https://habr.com/ru/company/ruvds/blog/438796/

Метафоры и Docker
>Виртуальные контейнеры можно сравнить с обычными пластиковыми контейнерами.

Как и обычный пластиковый контейнер, контейнер Docker обладает следующими характеристиками:
+ В нём можно что-то хранить. Нечто может находиться либо в контейнере, либо за его пределами.
+ Его можно переносить. Контейнер Docker можно использовать на локальном компьютере, 
  на компьютере коллеги, на сервере поставщика облачных услуг (вроде AWS).
+ В контейнер удобно что-то класть и удобно что-то из него вынимать. 
  У контейнеров Docker есть нечто подобное, представляющее их интерфейс, то есть — механизмы, позволяющие им взаимодействовать с внешним миром. Например, у контейнера есть порты, которые можно открывать для того, чтобы к приложению, работающему в контейнере, можно было бы обращаться из браузера. Работать с контейнером можно и средствами командной строки.
+ Если вам нужен контейнер, его можно заказать в интернет-магазине.
  В случае с контейнерами Docker то, что можно сравнить с пресс-формой, а именно — образ контейнера, хранится в специальном репозитории. Если вам нужен некий контейнер, вы можете загрузить из репозитория соответствующий образ, и, используя его, этот контейнер создать.

>Контейнеры Docker — это вызванные к жизни образы Docker.

>В конце концов, контейнеры — это программы. И, на фундаментальном уровне, контейнер представляет собой набор инструкций, который выполняется на некоем процессоре, обрабатывая какие-то данные.
+ Во время выполнения контейнера Docker внутри него обычно выполняется какая-то программа. Она выполняет в контейнере некие действия, то есть — делает что-то полезное.

Благодаря использованию Docker можно, на одном и том же компьютере, одновременно запускать множество контейнеров. И, как и любые другие программы, контейнеры Docker можно запускать, останавливать, удалять. Можно исследовать их содержимое и создавать их.

#### Концепции Docker
> Виртуальные машины

Предшественниками контейнеров Docker были виртуальные машины. Виртуальная машина, как и контейнер, изолирует от внешней среды приложение и его зависимости. Однако контейнеры Docker обладают преимуществами перед виртуальными машинами. Так, они потребляют меньше ресурсов, их очень легко переносить, они быстрее запускаются и приходят в работоспособное состояние. В [этом](https://medium.freecodecamp.org/a-beginner-friendly-introduction-to-containers-vms-and-docker-79a9e3e119b) материале можно найти подробное сравнение контейнеров и виртуальных машин.

> Образ контейнера Docker

То, что в терминологии Docker называется «образом», или, по-английски, «image», это совсем не то же самое, что, например, фотография (это — одно из значений слова «image»).

Образы контейнеров Docker можно сравнить с чертежами, с формочками для печенья, или с пресс-формами для изготовления пластиковых изделий. Образы — это неизменные шаблоны, которые используются для создания одинаковых контейнеров.

В образе контейнера Docker содержится образ базовой операционной системы, код приложения, библиотеки, от которого оно зависит. Всё это скомпоновано в виде единой сущности, на основе которой можно создать контейнер.

> Файл Dockerfile

Файл Dockerfile содержит набор инструкций, следуя которым Docker будет собирать образ контейнера. Этот файл содержит описание базового образа, который будет представлять собой исходный слой образа.
 
В образ контейнера, поверх базового образа, можно добавлять дополнительные слои. Делается это в соответствии с инструкциями из Dockerfile. Например, если Dockerfile описывает образ, который планируется использовать для решения задач машинного обучения, то в нём могут быть инструкции для включения в промежуточный слой такого образа библиотек NumPy, Pandas и Scikit-learn.

 И, наконец, в образе может содержаться, поверх всех остальных, ещё один тонкий слой, данные, хранящиеся в котором, поддаются изменению. Это — небольшой по объёму слой, содержащий программу, которую планируется запускать в контейнере

> Контейнер Docker

Для того чтобы запустить контейнер, нам нужен, во-первых, образ контейнера, во-вторых — среда, в которой установлен Docker, способная понять команду вида `docker run image_name`. Эта команда создаёт контейнер из образа и запускает его.

> Репозиторий контейнеров

Если вы хотите дать возможность другим людям создавать контейнеры на основе вашего образа, вы можете отправить этот образ в облачное хранилище. Самым крупным подобным хранилищем является репозиторий [Docker Hub](https://hub.docker.com/). Он используется при работе с Docker по умолчанию.


Docker notes
---
+ [Цикл статей по докеру на хабре, основы - отличные метафоры для понимания](https://habr.com/ru/company/ruvds/blog/438796/)
+ [Хекслет](https://guides.hexlet.io/docker/)
+ [Хабр, длиннопост](https://habr.com/ru/post/310460/)

Офф.документация:
+ https://docs.docker.com
+ https://docs.docker.com/develop/
+ https://docs.docker.com/engine/
+ https://docs.docker.com/reference/

Для тренировки, "поиграться"
+ https://labs.play-with-docker.com
+ https://training.play-with-docker.com/ops-stage1/

>В работе Докера есть одна деталь, которую важно знать при установке на Mac и Linux. По умолчанию Докер работает через unix сокет. В целях безопасности сокет закрыт для пользователей, не входящих в группу docker. И хотя установщик добавляет текущего пользователя в эту группу автоматически, Докер сразу не заработает. Дело в том, что если пользователь меняет группу сам себе, то ничего не изменится до тех пор, пока пользователь не перелогинится. Такова особенность работы ядра. Для проверки того, в какие группы входит ваш пользователь, можно набрать команду id

`docker-compose up -d`

`docker info` информация о конфигурации самого Докера, статистика работы
`docker ps -aq` List all containers (only IDs)

`docker ps` - показать список запущенных контейнеров 
Некоторые флаги:
+ `-a` / `--all` — список всех контейнеров (по умолчанию показывает только запущенные);
+ `-q` / `--quiet` — перечислить только id контейнеров (полезно, когда вам нужны все контейнеры).

`docker pull` - чтобы скачать определённый образ или набор образов (репозиторий) (Docker Hub)

`docker build` — эта команда собирает образ Docker из Dockerfile и «контекста». 
Контекст сборки — это набор файлов, расположенных по определённому пути или URL. 
+ флаг `-t`, чтобы задать имя образа. 
+ `docker build -t my_container .` соберёт образ, используя текущую директорию, на что указывает точка в конце.

`docker run` - запускает контейнер, на основе указанного образа

`docker run -t name`

`docker run my_image -it bash` - запустит контейнер, а затем запустит в нём bash.

`docker logs` — эта команда используется для просмотра логов указанного контейнера. 
+ флаг `--follow`, чтобы следить за логами работающей программы: `docker logs --follow my_container`.

`docker volume ls` — показывает список томов, которые являются предпочитаемым механизмом для сохранения данных, генерируемых и используемых контейнерами Docker.

`docker rm` — удаляет один и более контейнеров, например, `docker rm my_container`.

`docker rmi` — удаляет один и более образов, например, `docker rmi my_image`.

`docker stop`  —  останавливает один и более контейнеров

`docker stop my_container` - остановит один контейнер

`docker stop $(docker ps -a -q)` — остановит все запущенные

`docker stop $(docker ps -aq)` Stop all running containers

`docker kill my_container` - не пытается сначала аккуратно завершить процесс

`docker kill $(docker ps -q)` - останавливаем все запущенные контейнеры

`docker rm $(docker ps -a -q)` - удаляем все остановленные контейнеры

`docker rm $(docker ps -aq)` Remove all containers

`docker rmi $(docker images -q)` - удаляем все образы

`docker rmi $(docker images -q)` Remove all images

`docker build -t task2 .`

`docker run -p 8082:2000 --env host=http://localhost:8081  -t task2`

`docker build -t task1 ./service-1-java/ `

`docker build -t task2 ./service-2-kt/`

### Docker Networks
https://docs.docker.com/network/bridge/
`docker network create myNetwork`
`docker network inspect myNetwork`
`docker network connect myNetwork container1`
`docker network connect myNetwork container2`
`docker network ls `
docker network commands:
  connect     Connect a container to a network
  create      Create a network
  disconnect  Disconnect a container from a network
  inspect     Display detailed information on one or more networks
  ls          List networks
  prune       Remove all unused networks
  rm          Remove one or more networks


### Дюжина инструкций Dockerfile
+ FROM — задаёт базовый (родительский) образ.
+ LABEL — описывает метаданные. Например — сведения о том, кто создал и поддерживает образ.
+ ENV — устанавливает постоянные переменные среды.
+ RUN — выполняет команду и создаёт слой образа. Используется для установки в контейнер пакетов.
+ COPY — копирует в контейнер файлы и папки.
+ ADD — копирует файлы и папки в контейнер, может распаковывать локальные .tar+файлы.
+ CMD — описывает команду с аргументами, которую нужно выполнить когда контейнер будет запущен. Аргументы могут быть переопределены при запуске контейнера. В файле может присутствовать лишь одна инструкция CMD.
+ WORKDIR — задаёт рабочую директорию для следующей инструкции.
+ ARG — задаёт переменные для передачи Docker во время сборки образа.
+ ENTRYPOINT — предоставляет команду с аргументами для вызова во время выполнения контейнера. Аргументы не переопределяются.
+ EXPOSE — указывает на необходимость открыть порт.
+ VOLUME — создаёт точку монтирования для работы с постоянным хранилищем.

```
Docker Containers with common Networks 

Create two containers:
	docker run -d --name web1  -p 8001:80 eboraas/apache-php
	docker run -d --name web2  -p 8002:80 eboraas/apache-php
Important note: it is very important to explicitly specify a name with --name for your containers otherwise I’ve noticed that it would not work with the random names that Docker assigns to your containers.
Then create a new network:
	docker network create myNetwork
After that connect your containers to the network:
	docker network connect myNetwork web1
	docker network connect myNetwork web2 
Check if your containers are part of the new network:
	docker network inspect myNetwork
Then test the connection:
	docker exec -ti web1 ping web2
Again it is quite important to explicitly specify names for your containers otherwise this would not work. I figured this out after spending a few hours trying to figure it out
```


### KATACODE
- https://www.katacoda.com/learn
- https://www.katacoda.com/courses/docker/deploying-first-container

> registry.hub.docker.com/ or by using
> `docker search <name>`

The Docker CLI has a command called run which will start a container based on a Docker Image.
>`docker run <options> <image-name>`

By default, Docker will run a command in the foreground. To run in the background, the option -d needs to be specified.
>`docker run -d redis`
>`docker run -d redis:latest`

By default, Docker will run the latest version available. If a particular version was required, it could be specified as a tag, for example, version 3.2 would be `docker run -d redis:3.2`.

> `docker ps`

> ##### The command provides more details about a running container, such as IP address.
> `docker inspect <friendly-name|container-id>`

> ##### The command will display messages the container has written to standard error or standard out
> `docker logs <friendly-name|container-id>`

> `docker run -d --name -p <host-port>:<container-port>`

> `docker run -d --name redisHostPort -p 6379:6379 redis:latest`

##### Protip
By default, the port on the host is mapped to 0.0.0.0, which means all IP addresses. You can specify a particular IP address when you define the port mapping, for example, -p 127.0.0.1:6379:6379

The problem with running processes on a fixed port is that you can only run one instance. You would prefer to run multiple Redis instances and configure the application depending on which port Redis is running on.

> ##### Using the option -p 6379 enables to expose Redis but on a randomly available port
> - `docker run -d --name redisDynamic -p 6379 redis:latest`
> - `docker port redisDynamic 6379`

Containers are designed to be stateless. Binding directories (also known as volumes) is done using the option `-v <host-dir>:<container-dir>`. When a directory is mounted, the files which exist in that directory on the host can be accessed by the container and any data changed/written to the directory inside the container will be stored on the host. This allows you to upgrade or change containers without losing your data.

The official Redis image stores logs and data into a `/data` directory.
Any data which needs to be saved on the Docker Host, and not inside containers, should be stored in `/opt/docker/data/redis`.

> `run -d --name redisMapped -v /opt/docker/data/redis:/data redis`

##### Protip
Docker allows you to use `$PWD` as a placeholder for the current directory.

 If you wanted to interact with the container (for example, to access a bash shell) she could include the options `-it`.

> ##### The command launches an Ubuntu container and executes the command ps to view all the processes running in a container.
> `docker run ubuntu ps`

> Using the command allows you to get access to a bash shell inside of a container.
> `docker run -it ubuntu bash`









