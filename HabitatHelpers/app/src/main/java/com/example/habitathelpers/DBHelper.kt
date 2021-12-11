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
        db.execSQL(CREATE_TABLE_HABS)
        db.execSQL(CREATE_TABLE_PETS)
        initializeTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DROP_TABLE_HABS)
        db.execSQL(DROP_TABLE_PETS)
        closeDB()
    }

    private fun initializeTables(db: SQLiteDatabase){
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

    private fun closeDB(){
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
        petList = mutableListOf()
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
        habList = mutableListOf()
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

    private fun insertAll(db: SQLiteDatabase?) = runBlocking {
        //test data
        db!!.execSQL(TEST_DATAP1)
        db.execSQL(TEST_DATAP2)
        db.execSQL(TEST_DATAP3)
        db.execSQL(TEST_DATAP4)
        db.execSQL(TEST_DATAH1)
        db.execSQL(TEST_DATAH2)
    }

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
