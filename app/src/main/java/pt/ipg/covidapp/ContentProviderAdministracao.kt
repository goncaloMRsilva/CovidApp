package pt.ipg.covidapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderCovidApp : ContentProvider(){
    private var bdAdministracaoOpenHelper : BdAdministracaoOpenHelper? = null

    override fun onCreate(): Boolean {
        bdAdministracaoOpenHelper = BdAdministracaoOpenHelper(context)

        return true
    }

    override fun query(
            uri: Uri,
            projection: Array<out String>?,
            selection: String?,
            selectionArgs: Array<out String>?,
            sortOrder: String?
    ): Cursor? {
        val bd = bdAdministracaoOpenHelper!!.readableDatabase

        return when(getUriMatcher().match(uri)){
            URI_VACINAS -> TabelaVacinas(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_VACINA_ESPECIFICO -> TabelaVacinas(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            URI_UTENTES -> TabelaUtentes(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_UTENTE_ESPECIFICO ->TabelaUtentes(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            URI_PROFISSIONALSAUDE -> TabelaProfissionalSaude(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_PROFISSIONALSAUDE_ESPECIFICO ->TabelaProfissionalSaude(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            URI_CARGO -> TabelaCargo(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_CARGO_ESPECIFICO ->TabelaCargo(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when(getUriMatcher().match(uri)){
            URI_VACINAS -> "$MULTIPLOS_ITEMS/$VACINAS"
            URI_VACINA_ESPECIFICO -> "$UNICO_ITEM/$VACINAS"
            URI_UTENTES -> "$MULTIPLOS_ITEMS/$UTENTES"
            URI_UTENTE_ESPECIFICO -> "$UNICO_ITEM/$UTENTES"
            URI_PROFISSIONALSAUDE -> "$MULTIPLOS_ITEMS/$PROFISSIONALSAUDE"
            URI_PROFISSIONALSAUDE_ESPECIFICO -> "$UNICO_ITEM/$PROFISSIONALSAUDE"
            URI_CARGO -> "$MULTIPLOS_ITEMS/$CARGO"
            URI_CARGO_ESPECIFICO -> "$UNICO_ITEM/$CARGO"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdAdministracaoOpenHelper!!.writableDatabase

        val id = when(getUriMatcher().match(uri)){
            URI_VACINAS -> TabelaVacinas(bd).insert(values!!)
            URI_UTENTES -> TabelaUtentes(bd).insert(values!!)
            URI_PROFISSIONALSAUDE -> TabelaProfissionalSaude(bd).insert(values!!)
            URI_CARGO -> TabelaCargo(bd).insert(values!!)
            else -> -1
        }

        if (id == -1L) return null
        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdAdministracaoOpenHelper!!.writableDatabase

        return when(getUriMatcher().match(uri)){
            URI_VACINA_ESPECIFICO -> TabelaVacinas(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_UTENTE_ESPECIFICO -> TabelaUtentes(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_PROFISSIONALSAUDE_ESPECIFICO ->TabelaProfissionalSaude(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_CARGO_ESPECIFICO -> TabelaCargo(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            else -> 0
        }
    }

    override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<out String>?
    ): Int {
        val bd = bdAdministracaoOpenHelper!!.writableDatabase

        return when(getUriMatcher().match(uri)){
            URI_VACINA_ESPECIFICO -> TabelaVacinas(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_UTENTE_ESPECIFICO ->TabelaUtentes(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_PROFISSIONALSAUDE_ESPECIFICO ->TabelaProfissionalSaude(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_CARGO_ESPECIFICO ->TabelaCargo(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            else -> 0
        }
    }

    companion object{
        private const val AUTHORITY = "pt.ipg.trabalhofinal"
        private const val VACINAS = "vacinas"
        private const val UTENTES = "utentes"
        private const val PROFISSIONALSAUDE = "profissionalsaude"
        private const val CARGO = "cargo"

        private const val URI_VACINAS = 100
        private const val URI_VACINA_ESPECIFICO = 101
        private const val URI_UTENTES = 200
        private const val URI_UTENTE_ESPECIFICO = 201
        private const val URI_PROFISSIONALSAUDE = 300
        private const val URI_PROFISSIONALSAUDE_ESPECIFICO = 301
        private const val URI_CARGO = 400
        private const val URI_CARGO_ESPECIFICO = 401

        private const val MULTIPLOS_ITEMS = "vnd,android.cursor.dir"
        private const val UNICO_ITEM = "vnd,android.cursor.item"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        public val ENDERECO_VACINAS = Uri.withAppendedPath(ENDERECO_BASE, VACINAS)
        public val ENDERECO_UTENTES = Uri.withAppendedPath(ENDERECO_BASE, UTENTES)
        public val ENDERECO_PROFISSIONALSAUDE = Uri.withAppendedPath(ENDERECO_BASE, PROFISSIONALSAUDE)
        public val ENDERECO_CARGO = Uri.withAppendedPath(ENDERECO_BASE, CARGO)

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, VACINAS, 100)
            uriMatcher.addURI(AUTHORITY, "$VACINAS/#", 101)
            uriMatcher.addURI(AUTHORITY, UTENTES, 200)
            uriMatcher.addURI(AUTHORITY, "$UTENTES/#", 201)
            uriMatcher.addURI(AUTHORITY, PROFISSIONALSAUDE, 300)
            uriMatcher.addURI(AUTHORITY, "$PROFISSIONALSAUDE/#", 301)
            uriMatcher.addURI(AUTHORITY, CARGO, 400)
            uriMatcher.addURI(AUTHORITY, "$CARGO/#", 401)

            return uriMatcher
        }
    }

}