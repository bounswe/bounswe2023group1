The project is accessible at port 80, so just [http://localhost/]() should show the frontend. The backend is available at [http://localhost/api/]() 

To run the project in debug mode, run `docker-compose -f docker-compose_dev.yml up --build`. This allows the frontend to get updated every time you make a change automatically.

To run the prooject in production mode, run `docker-compose up`.