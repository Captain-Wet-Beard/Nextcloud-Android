/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.nextcloud.client.migrations

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@Suppress("MagicNumber")
class MockSharedPreferencesTest {

    private lateinit var mock: MockSharedPreferences

    @Before
    fun setUp() {
        mock = MockSharedPreferences()
    }

    @Test
    fun getSetStringSet() {
        val value = setOf("alpha", "bravo", "charlie")
        mock.edit().putStringSet("key", value).apply()
        val copy = mock.getStringSet("key", mutableSetOf())
        assertNotSame(value, copy)
        assertEquals(value, copy)
    }

    @Test
    fun getSetInt() {
        val value = 42
        val editor = mock.edit()
        editor.putInt("key", value)
        assertEquals(100, mock.getInt("key", 100))
        editor.apply()
        assertEquals(42, mock.getInt("key", 100))
    }

    @Test
    fun getSetBoolean() {
        val value = true
        val editor = mock.edit()
        editor.putBoolean("key", value)
        assertFalse(mock.getBoolean("key", false))
        editor.apply()
        assertTrue(mock.getBoolean("key", false))
    }

    @Test
    fun getSetString() {
        val value = "a value"
        val editor = mock.edit()
        editor.putString("key", value)
        assertEquals("default", mock.getString("key", "default"))
        editor.apply()
        assertEquals("a value", mock.getString("key", "default"))
    }

    @Test
    fun getAll() {
        // GIVEN
        //      few properties are stored in shared preferences
        mock.edit()
            .putInt("int", 1)
            .putBoolean("bool", true)
            .putString("string", "value")
            .putStringSet("stringSet", setOf("alpha", "bravo"))
            .apply()
        assertEquals(4, mock.store.size)

        // WHEN
        //      all properties are retrieved
        val all = mock.all

        // THEN
        //      returned map is a different instance
        //      map is equal to internal storage
        assertNotSame(all, mock.store)
        assertEquals(all, mock.store)
    }
}
