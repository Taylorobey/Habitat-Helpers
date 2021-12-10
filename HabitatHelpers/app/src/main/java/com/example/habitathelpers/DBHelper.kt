package com.example.habitathelpers

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.runBlocking


class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    // Declare variables for pet and hab lists
    private lateinit var petList: MutableList<Pet>
    private lateinit var habList: MutableList<Hab>

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(DROP_TABLE_HABS)
        db.execSQL(DROP_TABLE_PETS)
        db!!.execSQL(CREATE_TABLE_HABS)
        db.execSQL(CREATE_TABLE_PETS)
        initializeTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DROP_TABLE_HABS)
        db.execSQL(DROP_TABLE_PETS)
        closeDB()
    }

    fun initializeTables(db: SQLiteDatabase){
        val queryp = "SELECT * FROM pets"
        val p = db.rawQuery(queryp, null)
        val queryh = "SELECT * FROM habs"
        val h = db.rawQuery(queryh, null)
        if( p.count <= 0 || h.count <= 0){
            //initialize pets table
            insertAll(db)
        }
        p.close()
        h.close()
    }

    fun closeDB(){
        val db = this.readableDatabase
        if(db != null && db.isOpen) {
            db.close()
        }
    }

    fun getPetSize(): Int {
        val db = this.readableDatabase
        val query = "SELECT * FROM pets"
        val c = db.rawQuery(query, null)
        val size = c.count
        c.close()
        return size
    }

    fun getHabSize(): Int {
        val db = this.readableDatabase
        val query = "SELECT * FROM habs"
        val c = db.rawQuery(query, null)
        val size = c.count
        c.close()
        return size
    }

    @SuppressLint("Range")
    fun getAllPets(): MutableList<Pet>{
        petList = mutableListOf<Pet>()
        val db = this.readableDatabase
        val query = "SELECT * FROM pets"
        val c = db.rawQuery(query, null)
        c.moveToFirst()
        while (!c.isAfterLast){
            if (c.getString(c.getColumnIndex(COL_SPECIES)) != null) {
                val newPet = getPet(c.getString(c.getColumnIndex(COL_SPECIES)))
                petList.add(newPet)
            }
            c.moveToNext()
        }
        c.close()
        return petList
    }

    @SuppressLint("Range")
    fun getAllHabs(): MutableList<Hab>{
        habList = mutableListOf<Hab>()
        val db = this.readableDatabase
        val query = "SELECT * FROM habs"
        val c = db.rawQuery(query, null)
        c.moveToFirst()
        while (!c.isAfterLast){
            if (c.getString(c.getColumnIndex(COL_MATERIAL)) != null) {
                val newHab = getHab(c.getString(c.getColumnIndex(COL_MATERIAL)))
                habList.add(newHab)
            }
            c.moveToNext()
        }
        c.close()
        return habList
    }

    @SuppressLint("Range")
    fun getPet(petSpec: String): Pet{
        val query = "SELECT * FROM pets WHERE species='$petSpec'"
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        c.moveToFirst()
        val newPet = Pet(
            // TODO: Finalize pet data class attributes, retrieve here
            "name",
            c.getString(c.getColumnIndex(COL_SPECIES)),
            "gender",
            1
        )
        c.close()
        return newPet
    }

    @SuppressLint("Range")
    fun getHab(habMat: String): Hab{
        val query = "SELECT * FROM habs WHERE material='$habMat'"
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        c.moveToFirst()
        val newHab = Hab(
            // TODO: Finalize enclosure data class attributes, retrieve here
            "name",
            c.getString(c.getColumnIndex(COL_MATERIAL)),
            c.getString(c.getColumnIndex(COL_SUBSTRATE)),
            0,
            0,
            0
        )
        c.close()
        return newHab
    }


    fun insertAll(db: SQLiteDatabase?) = runBlocking {
        //test data
        db!!.execSQL(TEST_DATAP1)
        db.execSQL(TEST_DATAP2)
        db.execSQL(TEST_DATAP3)
        db.execSQL(TEST_DATAP4)
        db.execSQL(TEST_DATAH1)
        db.execSQL(TEST_DATAH2)
    }

/*    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun insertAllHabs(db: SQLiteDatabase?){
        // load test data to local database
        val dbFile= openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READWRITE)
        var query = "SELECT * FROM habs"
        val c = dbFile.rawQuery(query, null)
        c.moveToFirst()
        while (!c.isAfterLast){
            query = "INSERT INTO habs (id, material, substrate) VALUES"
            if (db != null) {
                db.rawQuery((query + c.getInt(c.getColumnIndex(COL_ID)) + c.getString(c.getColumnIndex(
                    COL_MATERIAL)) + c.getString(c.getColumnIndex(COL_SUBSTRATE))), null)
            }
            c.moveToNext()
        }
        c.close()
        dbFile.close()
    }*/

    /*db_id = c.getInt(c.getColumnIndex(COL_ID)),
checked = false,
backdrop_path = c.getString(c.getColumnIndex(COL_BACKDROP)),
genre_ids = emptyList(),
id = c.getInt(c.getColumnIndex(COL_MOVIE_ID)),
original_language = c.getString(c.getColumnIndex(COL_ORG_LANG)),
original_title =  c.getString(c.getColumnIndex(COL_ORG_TITLE)),
overview = c.getString(c.getColumnIndex(COL_OVERVIEW)),
popularity = c.getDouble(c.getColumnIndex(COL_POPULARITY)),
poster_path = c.getString(c.getColumnIndex(COL_POSTER)),
release_date = c.getString(c.getColumnIndex(COL_RELEASE)),
title = c.getString(c.getColumnIndex(COL_TITLE)),
video = true,
vote_average = c.getDouble(c.getColumnIndex(COL_VOTE_AVG)),
vote_count= c.getInt(c.getColumnIndex(COL_VOTE_CNT)),*/

/*    fun getAllMovies(db: DatabaseHelper): MutableList<MovieData> {
        var dataset : MutableList<MovieData> = mutableListOf<MovieData>()
        for (i in 0 until db.getSize()) {
            dataset.add(db.getMovie(i))
        }
        return dataset
    }

    fun insertAllMovies(){
        val myStaticMovie = this.movieList
        for(movie in myStaticMovie){
            val id = addMovie(movie)
        }
    }

    fun addMovie(movie: MovieData): Long{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_MOVIE_ID, movie.id)
        values.put(COL_VOTE_AVG, movie.vote_average)
        values.put(COL_TITLE, movie.title)
        values.put(COL_ORG_TITLE, movie.original_title)
        values.put(COL_ORG_LANG, movie.original_language)
        values.put(COL_OVERVIEW, movie.overview)
        values.put(COL_POPULARITY, movie.popularity)
        values.put(COL_POSTER, movie.poster_path)
        values.put(COL_BACKDROP, movie.backdrop_path)
        values.put(COL_VOTE_CNT, movie.vote_count)
        values.put(COL_RELEASE, movie.release_date)
        return db.insert("movies", null, values)
    }

    @SuppressLint("Range")
    fun getMovie(pos: Int): MovieData {
        val query = "SELECT * FROM movies"
        val db = this.readableDatabase
        val c = db.rawQuery(query, null)
        c.moveToPosition(pos)
        val movie = MovieData(
            db_id = c.getInt(c.getColumnIndex(COL_ID)),
            checked = false,
            backdrop_path = c.getString(c.getColumnIndex(COL_BACKDROP)),
            genre_ids = emptyList(),
            id = c.getInt(c.getColumnIndex(COL_MOVIE_ID)),
            original_language = c.getString(c.getColumnIndex(COL_ORG_LANG)),
            original_title =  c.getString(c.getColumnIndex(COL_ORG_TITLE)),
            overview = c.getString(c.getColumnIndex(COL_OVERVIEW)),
            popularity = c.getDouble(c.getColumnIndex(COL_POPULARITY)),
            poster_path = c.getString(c.getColumnIndex(COL_POSTER)),
            release_date = c.getString(c.getColumnIndex(COL_RELEASE)),
            title = c.getString(c.getColumnIndex(COL_TITLE)),
            video = true,
            vote_average = c.getDouble(c.getColumnIndex(COL_VOTE_AVG)),
            vote_count= c.getInt(c.getColumnIndex(COL_VOTE_CNT)),
        )
        return movie
    }

    */

/*    fun updateMovie(movie: MovieData): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_MOVIE_ID, movie.id)
        values.put(COL_VOTE_AVG, movie.vote_average)
        values.put(COL_TITLE, movie.title)
        values.put(COL_ORG_TITLE, movie.original_title)
        values.put(COL_ORG_LANG, movie.original_language)
        values.put(COL_OVERVIEW, movie.overview)
        values.put(COL_POPULARITY, movie.popularity)
        values.put(COL_POSTER, movie.poster_path)
        values.put(COL_BACKDROP, movie.backdrop_path)
        values.put(COL_VOTE_CNT, movie.vote_count)
        values.put(COL_RELEASE, movie.release_date)
        return db.update("movies", values, "COL_ID = ?", arrayOf(movie.db_id.toString()))
    }*/

    companion object {
        private val DB_NAME = "hhelper.db"
        private val DB_VER = 1
        private val COL_ID = "id"
        private val COL_NAME = "name"
        // values for pets table
        private val COL_SPECIES = "species"
        private val COL_GENDER = "gender"
        private val COL_AGE = "age"

        //values for habitat table
        private val COL_MATERIAL = "material"
        private val COL_SUBSTRATE = "substrate"
        private val COL_WIDTH = "width"
        private val COL_LENGTH= "length"
        private val COL_HEIGHT = "height"

        // create table pets
        private val CREATE_TABLE_PETS = "CREATE TABLE IF NOT EXISTS pets " +
                "( $COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_SPECIES TEXT, " +
                "$COL_GENDER TEXT, $COL_AGE INTEGER )"
        // create table habs
        private val CREATE_TABLE_HABS = "CREATE TABLE IF NOT EXISTS habs " +
                "( $COL_ID INTEGER PRIMARY KEY, $COL_MATERIAL TEXT, $COL_SUBSTRATE TEXT, $COL_WIDTH INTEGER, " +
                "$COL_LENGTH INTEGER, $COL_HEIGHT INTEGER )"


        //delete table pets
        private val DROP_TABLE_PETS = "DROP TABLE IF EXISTS pets"
        //delete table habs
        private val DROP_TABLE_HABS = "DROP TABLE IF EXISTS habs"

        //adding test data
        private val TEST_DATAP1 = "INSERT INTO pets ($COL_ID, $COL_SPECIES) VALUES (1, 'Ball Python')"
        private val TEST_DATAP2 = "INSERT INTO pets (id, species) VALUES (2, 'Leopard Gecko')"
        private val TEST_DATAP3 = "INSERT INTO pets (id, species) VALUES (3, 'Crested Gecko')"
        private val TEST_DATAP4 = "INSERT INTO pets (id, species) VALUES (4, 'Bearded Dragon')"
        private val TEST_DATAH1 = "INSERT INTO habs (id, material, substrate) VALUES (1, 'Plastic', 'Paper')"
        private val TEST_DATAH2 = "INSERT INTO habs (id, material, substrate) VALUES (2, 'Glass', 'Coconut Fiber')"
    }
}
