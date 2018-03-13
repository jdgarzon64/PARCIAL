package Infraestructura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Conexion extends SQLiteOpenHelper {

    private static final String database = "cartera.db";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 2;
    SQLiteDatabase bd;
    private String sql;


    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Conexion(Context context) {
        super(context, database, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            //db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE deudores ( " +
                "deudor_id integer primary key autoincrement not null, " +
                "nombre TEXT NOT NULL, " +
                "monto TEXT NOT NULL,  " +
                "rutafoto TEXT NOT NULL  " +
                ")");

        db.execSQL("CREATE TABLE prestamos ( " +
                "prestamo_id integer primary key autoincrement not null, " +
                "nombre TEXT NOT NULL, " +
                "monto TEXT NOT NULL,  " +
                "rutafoto TEXT NOT NULL  " +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists deudores");
        db.execSQL("drop table if exists prestamos");
        onCreate(db);
    }

    public void cerrarConexion() {
        bd.close();
    }

    /**
     * ejecuta un insert
     *
     * @param tabla    la tabla en la que se va a insertar
     * @param registro los datos a insertar
     * @return true si inserta, de lo contrario false
     */
    public boolean ejecutarInsert(String tabla, ContentValues registro) {
        try {
            bd = this.getWritableDatabase();
            int res = (int) bd.insert(tabla, null, registro);
            if (res != -1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ejecuta el modificar
     *
     * @param tabla     la taba a modificar
     * @param condicion la condicion en la que se va a modificar
     * @param registro  los datos a modificar
     * @return true si modifica, de lo contrario false
     */
    public boolean ejecutarUpdate(String tabla, String condicion, ContentValues registro) {
        try {
            bd = this.getWritableDatabase();
            int cant = bd.update(tabla, registro, condicion, null);
            cerrarConexion();
            if (cant == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ejecuta las busquedas
     *
     * @param consulta el dato a buscar
     * @return el objeto si lo encuentra, de lo contrario false
     */
    public Cursor ejecutarSearch(String consulta) {
        try {
            bd = this.getWritableDatabase();
            Cursor fila = bd.rawQuery(consulta, null);
            return fila;
        } catch (Exception e) {
            cerrarConexion();
            return null;
        }
    }

    /**
     * elimina en la bd
     *
     * @param tabla     la tabla en la que se eliminara
     * @param condicion el dato a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean ejecutarDelete(String tabla, String condicion) {
        try {
            bd = this.getWritableDatabase();
            int cant = bd.delete(tabla, condicion, null);
            if (cant == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
