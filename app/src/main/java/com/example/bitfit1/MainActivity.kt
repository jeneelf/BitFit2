package com.example.bitfit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit1.ui.theme.BitFit1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val entries = mutableListOf<DisplayEntry>()


    private lateinit var adapter: EntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = EntryAdapter(entries)
        findViewById<RecyclerView>(R.id.rvEntries).adapter = adapter

        val db = (application as WaterApplication).db

        lifecycleScope.launch {
            db.waterEntryDao().getAllEntries().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayEntry(entity.date, entity.quantity, entity.notes)
                }.also { mappedList ->
                    entries.clear()
                    entries.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        findViewById<Button>(R.id.btnAddEntry).setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
        }
    }
}


