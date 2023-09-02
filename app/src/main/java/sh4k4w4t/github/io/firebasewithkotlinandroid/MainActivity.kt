package sh4k4w4t.github.io.firebasewithkotlinandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import sh4k4w4t.github.io.firebasewithkotlinandroid.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var dataList = mutableListOf<User>()

    private var userAdapter: MainActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("data")
        userAdapter = databaseReference?.let { MainActivityAdapter(this) }

        initRecyclerView()

        getData()

        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            amRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            amRecyclerView.adapter = userAdapter
        }
    }

    private fun saveData() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val user = User(first_name = firstName, last_name = lastName)
        databaseReference?.child(generateRandomId(5))?.setValue(user)
    }

    private fun getData() {
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (ds in snapshot.children) {
                    val singleUserId = ds.key
                    val singleUserFirstName = ds.child("first_name").value.toString()
                    val singleUserLastName = ds.child("last_name").value.toString()
                    val user = User(
                        id = singleUserId,
                        first_name = singleUserFirstName,
                        last_name = singleUserLastName
                    )
                    dataList.add(user)
                }
                userAdapter?.setDataSet(dataList, this@MainActivity)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: Cancel -> " + error.toException())
            }
        })
    }


    override fun onItemClickForUpdate(user: User) {
        DataController.setSelectedUser(user)
        val intent = Intent(this, UpdateActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClickForDelete(user: User) {
        val selectedUser = user.id
        if (selectedUser != null) {
            databaseReference?.child(selectedUser)?.removeValue()
        }
    }
}


fun generateRandomId(length: Int): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = Random(System.currentTimeMillis())

    return (1..length)
        .map { characters[random.nextInt(characters.length)] }
        .joinToString("")
}