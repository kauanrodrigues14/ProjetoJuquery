import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class bdConnect(contexto: Context) : SQLiteOpenHelper(contexto, NOME_DO_BANCO_DE_DADOS, null, VERSAO_DO_BANCO_DE_DADOS) {

    companion object {
        private const val VERSAO_DO_BANCO_DE_DADOS = 1
        private const val NOME_DO_BANCO_DE_DADOS = "projetojuquery.db"
    }

    override fun onCreate(bd: SQLiteDatabase) {
        val CRIAR_TABELA_BOMBEIRO = ("CREATE TABLE Bombeiro(" +
                "idbombeiro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(50)," +
                "cpf CHAR(11)," +
                "login VARCHAR(7)," +
                "cargo VARCHAR(15)," +
                "senha VARCHAR(15))")

        val CRIAR_TABELA_SENSOR = ("CREATE TABLE Sensor(" +
                "idsensor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "posição CHAR(3))")

        val CRIAR_TABELA_ALERTA = ("CREATE TABLE Alerta(" +
                "idalerta INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fk_idsensor INTEGER," +
                "datahora DATETIME," +
                "temperatura INTEGER," +
                "umidade INTEGER," +
                "vento INTEGER," +
                "FOREIGN KEY (fk_idsensor) REFERENCES Sensor(idsensor))")

        bd.execSQL(CRIAR_TABELA_BOMBEIRO)
        bd.execSQL(CRIAR_TABELA_SENSOR)
        bd.execSQL(CRIAR_TABELA_ALERTA)

        val INSERIR_BOMBEIRO = ("INSERT INTO Bombeiro VALUES(\"1\",\"kauan\",\"21312312312\",\"kauan\",\"kauan\",\"kauan\")")
        bd.execSQL(INSERIR_BOMBEIRO)
    }

    override fun onUpgrade(bd: SQLiteDatabase, versaoAntiga: Int, novaVersao: Int) {
        bd.execSQL("DROP TABLE IF EXISTS Bombeiro")
        bd.execSQL("DROP TABLE IF EXISTS Sensor")
        bd.execSQL("DROP TABLE IF EXISTS Alerta")
        onCreate(bd)
    }

    fun cadastroBombeiro(nome: String, cpf:String, login:String, cargo:String, senha:String):Long{
        val bd = this.writableDatabase

        val valores = ContentValues().apply{
            put("nome", nome)
            put("cpf", cpf)
            put("login", login)
            put("cargo", cargo)
            put("senha", senha)
        }
        return bd.insert("Bombeiro", null, valores)
    }


    fun autenticarUsuario(login: String, senha: String): Boolean {
        val bd = this.writableDatabase

        val cursor = bd.rawQuery("SELECT * FROM Bombeiro WHERE login = ? AND senha = ?", arrayOf(login, senha))

        val usuarioExiste = cursor.count > 0

        cursor.close()

        return usuarioExiste


    }
}