package com.glass.mouher.database

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

/**
 * Class which manages realm local database schema migration
 * See <a href="https://realm.io/docs/java/latest/#migrations">Realm migration</a>
 */
class DatabaseMigration: RealmMigration {

    /**
     * Changelog :
     *  - Version 1: Added `codeReason` field in the `PODDatasDb` model
     */
    companion object {
        const val latestVersion = 4L
    }

    /**
     * @see [RealmMigration.migrate]
     */
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if (oldVersion < latestVersion) {
            if (schema.get("PODDatasDb")?.hasField("codeReason") == false) {
                schema.get("PODDatasDb")
                    ?.addField("codeReason", String::class.java, FieldAttribute.REQUIRED)
                    ?.transform {
                        it.set("codeReason", "")
                    }
            }
        }
    }
}