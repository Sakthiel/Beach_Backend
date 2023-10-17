
MOVIE_IDS=('tt6644200' 'tt6857112' 'tt7784604' 'tt5052448' 'tt1396484' 'tt5968394' 'tt4972582' 'tt6823368' 'tt1179933' )

declare -a slot_ids
declare -a screen_ids
declare start_date
declare next_date

OS_TYPE=$(uname -s)

number_of_weeks=$2


DB_HOST='localhost'
DB_DATABASE='pulpticket'
DB_USERNAME='sakthi'
DB_PASSWORD='sakthi'

get_random_movie_id () {
  lower_index=0
  upper_index=$(( ${#MOVIE_IDS[@]} - 1 ))
  echo ${MOVIE_IDS[$(( $RANDOM % (( $upper_index - $lower_index + 1 )) + $lower_index ))]}
}

get_random_price_with_two_decimal_places () {
  price_lower_value=150
  price_upper_value=300
  echo $(( $RANDOM % (( $price_upper_value - $price_lower_value + 1 )) + $price_lower_value )).$(( RANDOM % 99 ))
}

get_slot_ids_from_db () {
  slot_ids=($(PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qtc "select id from slot"))
}

get_screen_ids_from_db () {
  screen_ids=($(PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qtc "select id from screen"))
}

initialise_dates () {
  if [ "$OS_TYPE" == "Darwin" ]
  then
    start_date=$(date -j -f %Y-%m-%d $1 +%Y-%m-%d)
  else
    start_date=$(date --date "$1" "+%Y-%m-%d")
  fi
  next_date=${start_date}
}

get_next_date () {
  if [ "$OS_TYPE" == "Darwin" ]
  then
    next_date_command="date -j -f %Y-%m-%d -v+1d $1 +%Y-%m-%d"
  else
    next_date_command="date --date \"$1 +1 day\" \"+%Y-%m-%d\""
  fi
  echo $(eval $next_date_command)
}

seed_data_for_first_day () {
  for ((screen_id = ${screen_ids[0]} ; screen_id <= ${screen_ids[${#screen_ids[@]} - 1]} ; screen_id++));
  do
    for ((slot_id = ${slot_ids[0]}; slot_id <= ${slot_ids[ ${#slot_ids[@]} - 1 ]}; slot_id++));
    do
    movie_id=$( get_random_movie_id )
    price=$( get_random_price_with_two_decimal_places )
    PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qc \
    "insert into show (movie_id, date, slot_id, screen_id ,cost) values ('$movie_id', '${start_date}', $slot_id, $screen_id , $price)"
    done
  done

}

seed_data_second_day_onwards () {
  next_date=$( get_next_date "$start_date" )
  for ((day = 2; day <= $1; day++));
  do
    for ((screen_id = ${screen_ids[0]} ; screen_id <= ${screen_ids[${#screen_ids[@]} - 1]} ; screen_id++));
      do
        for ((slot_id = ${slot_ids[0]}; slot_id <= ${slot_ids[ ${#slot_ids[@]} - 1 ]}; slot_id++));
        do
        movie_id=$( get_random_movie_id )
        price=$( get_random_price_with_two_decimal_places )
        PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qc \
        "insert into show (movie_id, date, slot_id, screen_id ,cost) values ('$movie_id', '${next_date}', $slot_id, $screen_id , $price)"
        done
      done
    next_date=$( get_next_date "$next_date" )
  done
}

seed_movie_data () {
  echo "Seeding movie data in database..."

  PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qc \
  "insert into movies (id, name, duration, genre , rating , poster) values \
  ('tt6644200', 'A Quiet Place', 90, 'Horror', '7.5', 'https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg'), \
  ('tt6857112', 'Us', 116, 'Thriller', '8.5', 'https://m.media-amazon.com/images/M/MV5BZTliNWJhM2YtNDc1MC00YTk1LWE2MGYtZmE4M2Y5ODdlNzQzXkEyXkFqcGdeQXVyMzY0MTE3NzU@._V1_.jpg'), \
  ('tt7784604', 'Hereditary', 127, 'Horror', '6', 'https://m.media-amazon.com/images/M/MV5BOTU5MDg3OGItZWQ1Ny00ZGVmLTg2YTUtMzBkYzQ1YWIwZjlhXkEyXkFqcGdeQXVyNTAzMTY4MDA@._V1_.jpg'), \
  ('tt5052448', 'Get Out', 104, 'Horror', '9', 'https://m.media-amazon.com/images/M/MV5BMjUxMDQwNjcyNl5BMl5BanBnXkFtZTgwNzcwMzc0MTI@._V1_.jpg'), \
  ('tt1396484', 'It', 135, 'Horror', '8.9', 'https://m.media-amazon.com/images/M/MV5BZDVkZmI0YzAtNzdjYi00ZjhhLWE1ODEtMWMzMWMzNDA0NmQ4XkEyXkFqcGdeQXVyNzYzODM3Mzg@._V1_.jpg'), \
  ('tt5968394', 'Captive State', 109, 'Sci-Fi', '7', 'https://m.media-amazon.com/images/M/MV5BMTgyNjU0NTAxOV5BMl5BanBnXkFtZTgwNTc4MDIzNjM@._V1_.jpg'), \
  ('tt4972582', 'Split', 117, 'Action', '7.3', 'https://m.media-amazon.com/images/M/MV5BZTJiNGM2NjItNDRiYy00ZjY0LTgwNTItZDBmZGRlODQ4YThkL2ltYWdlXkEyXkFqcGdeQXVyMjY5ODI4NDk@._V1_.jpg'), \
  ('tt6823368', 'Glass', 129, 'Sci-Fi', '6.8', 'https://m.media-amazon.com/images/M/MV5BMTY1OTA2MjI5OV5BMl5BanBnXkFtZTgwNzkxMjU4NjM@._V1_.jpg'), \
  ('tt1179933', '10 Cloverfield Lane', 103, 'Horror', '7', 'https://m.media-amazon.com/images/M/MV5BMjEzMjczOTIxMV5BMl5BanBnXkFtZTgwOTUwMjI3NzE@._V1_.jpg')";

  echo "Movie data successfully seeded!"
}

seed_slot_data () {
  echo "Seeding slot data in database..."

  PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qc \
  "insert into slot (name, start_time, end_time) values \
  ('slot1', '09:00:00', '12:30:00'), \
  ('slot2', '13:30:00', '17:00:00'), \
  ('slot3', '18:00:00', '21:30:00'), \
  ('slot4', '22:30:00', '02:00:00')";

  echo "Slot data successfully seeded!"
}
clear_old_data () {
  echo "Truncating the following tables in database: movies , slot , screen ,show..."

  PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qc "truncate movies ,show , slot , screen";

  echo "Tables successfully truncated!"
}

seed_screen_data (){
   echo "Seeding screen data in database..."

   PGPASSWORD=${DB_PASSWORD} psql -h ${DB_HOST} -U ${DB_USERNAME} -d ${DB_DATABASE} -p '5435' -qc \
     "insert into screen (name) values \
     ('GoldScreen'), \
     ('SuperPlex'), \
     ('PlayHouse'), \
     ('4DX')";

     echo "Slot data successfully seeded!"

}

seed_show_data () {
  echo "Seeding show data in database..."

  number_of_days=$(( ${number_of_weeks} * 7 ))
  get_slot_ids_from_db
  get_screen_ids_from_db

  if [ $number_of_days -ne 0 ]
  then
    seed_data_for_first_day
    seed_data_second_day_onwards "$number_of_days"
  fi

  echo "Show data successfully seeded!"
}


initialise_dates "$1"
clear_old_data
seed_movie_data
seed_slot_data
seed_screen_data
seed_show_data