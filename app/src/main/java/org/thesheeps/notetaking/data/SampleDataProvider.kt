package org.thesheeps.notetaking.data

import java.util.*

class SampleDataProvider {

    companion object {
        private val sampleText1 = "Welcome to Note Taking App"
        private val sampleText2 = "This is a test App by\nThe Sheeps"
        private val sampleText3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque viverra nibh tortor, at molestie est porta fermentum. Aliquam congue mauris quis risus sollicitudin, tempus ornare lacus blandit. Cras eu bibendum mauris. Cras id ex quis risus dignissim tincidunt. Sed id pharetra est, vel semper mauris. Aenean convallis turpis in consectetur lacinia. Praesent ac purus posuere, aliquet metus sit amet, iaculis ipsum. Mauris in neque in massa facilisis bibendum. Aliquam erat volutpat. Vestibulum sit amet tellus a tortor volutpat dictum. Aliquam consectetur urna lectus, sit amet ultrices odio ullamcorper non. Curabitur nec odio ante. Duis ut quam quis justo sollicitudin scelerisque. Quisque id diam vel nunc scelerisque lobortis id at risus.

            Fusce vitae risus mattis, pretium lorem non, tempus erat. Pellentesque quis ante dignissim, lobortis ante at, aliquam tellus. Phasellus porttitor, nisl non feugiat ornare, sem elit rhoncus quam, ac dignissim orci felis vitae orci. Integer faucibus suscipit massa vitae pretium. Quisque a turpis elit. Cras odio lorem, tristique sit amet mattis vel, gravida nec ipsum. Nulla gravida arcu ipsum, eget suscipit urna finibus vitae. Aliquam semper leo nunc, tempus consectetur urna luctus ultricies.
        """.trimIndent()

        fun getNotes() = arrayListOf<NoteEntity>(
            NoteEntity(1, getDate(0), sampleText1),
            NoteEntity(2, getDate(1), sampleText2),
            NoteEntity(3, getDate(2), sampleText3)
        )

        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }
    }
}