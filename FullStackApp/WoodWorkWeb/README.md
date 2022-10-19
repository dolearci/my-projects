### Guide to run fullstack application
- There is docker-compose.yml file that builds application for you
- This file works with folders nextdocker and sbdocker downloads mysql image
- To run the application you need to have docker installed
- In WoodWorkWeb folder run the command docker-compose up
- This command will build images and run them in separate containers that are communicating together
- Wait for everything to download, application will be running on url http://localhost:3000
- There is url http://localhost:3000/addwoodwork when you can work with database and add woodworks

- In sbdocker folder is dockerfile that builds image to run SpringBoot backend container
- In nextdocker is folder with NextJS frontend (React framework) and dockerfile that builds image to run NextJS frontend container
