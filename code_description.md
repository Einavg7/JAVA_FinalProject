The first part of this project was to choose ten cities in Europe and gather data regarding these cities. 
The cities chosen: Berlin, Amsterdam, Athens, Budapest, Copenhagen, Lisbon, Ljubljana, Madrid, Paris and Rome.

For the second part of this project I created a City and Point class (UML chart) and imported the sql java package 
in order to use it for creating and reading the city data from a database.
I created a function called ```getCityData()``` to use this for fetching and displaying the database connection results, my city data. 
In the static function, I connected the class to a database using JDBC and postgreSQL url connection (the password and username are defined by each user in postgreSQL). 

Then, I created a table in the database with the sql ```CREATE``` statement and inserted my city data using the sql ```INSERT``` statement.
In order to make sure my data was created in the database I used the sql ```SELECT``` statement to retrieve my data. 
Next, I created city objects that match the number of rows I have in the table I created, and set all the object attributes using the table columns and parsing them into the correct datatype. 
In addition I created another function called ```dropTable()```, so each time the class is complied it will drop the table from the database and create a new one. Finally I created a cities object array and used the function ```getCityData()``` to store the data inside the cities object array.

In the next part of this project I wrote code to read and display my city data and all the Shape classes needed (UML chart added). 
In order to display correctly my cities, I created a function to calculate the bounding box of my cities coordinates. 
Then I added a padding to the bounding box so the cities would not be displayed on the border of the box. 

Then, I scaled my cities coordinates using the scale factor:
```{r}
double scale = 2500/Math.max(myBBox.length, myBBox.width);
```
The next step is to center my cities in the display window. This is done using these two calculations:
```{r}
city x coordinate = window.center.x – mybbox.center.x

city y coordinate = window.center.x – mybbox.center.x
```
My display window’s center coordinates are (500,500) and I used the ```Rectangle.centroid()``` method to find my bounding box’s coordinates. 
Then I modified my cities coordinates by adding the calculations result to the cities x and y coordinates. 
The last step is to flip my coordinates around the horizontal centerline, for this I subtracted every y-coordinate from 1000. 
After completing all required transformations I plot my cities on the graphic panel.

To decorate and display my data, I created a function to create buffers from the cities’ centroids. 
Then I calculated the population density for every 100 square kilometers in order to define the buffers diameters. 
For the buffers colors I normalized the population densities to values between 0 to 1 and used them as the saturation value for the 
HSB color – resulting in different shades of red. Then I created two bar charts, the first bar chart is created by using the total population and foreign population percentage for each city.

I created two rectangles that overlap, the main rectangle in yellow represents the number of citizens living in that city and the other rectangle in purple, represents the percentage of foreigners living in this city. 
The population bar chart height is fixed and the percentage of foreigners bar chart height is dependent on the real percentage compared to the fixed height. 

The third bar chart I created is to represent the GDP in billion dollars for each city. 
This bar chart is horizontal and is colored with the same method as the buffer are colored, using a different saturation value based on normalized GDP values for each city – resulting in different shades of green. 
After all the shapes are ready and added to the frame, I added labels accordingly – city names, GDP, total population, foreign population%. 

The last addition to my map was adding two legends that explain the colors for the GDP bar chart and the colors and sizes of the buffers. 
The GDP legend is created using three adjacent rectangles that are colored in shades of green using the minimum, median and maximum of the normalized GDP values as I used for the GDP city bar charts. 
The buffers legend is created using three circles that their sizes are defined by the minimum, median and maximum of the population density normalized values and colored using the same values and a polyline that connects between their centroids.
