package sh4k4w4t.github.io.firebasewithkotlinandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import sh4k4w4t.github.io.firebasewithkotlinandroid.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("data")

        val selectedUser = DataController.getSelectedUser()

        if (selectedUser != null) {
            val userId = selectedUser.id
            val firstName = selectedUser.first_name
            val lastName = selectedUser.last_name

            // Convert String to Editable
            val firstNameEditable = Editable.Factory.getInstance().newEditable(firstName)
            val lastNameEditable = Editable.Factory.getInstance().newEditable(lastName)

            // Set the text of EditText fields using data binding
            binding.auFastName.text = firstNameEditable
            binding.auLastName.text = lastNameEditable
            binding.auID.text = userId.toString()
        }

        binding.auUpdateButton.setOnClickListener {
            // Update data in Firebase when you want to save changes
            val updatedFirstName = binding.auFastName.text.toString()
            val updatedLastName = binding.auLastName.text.toString()

            // Create a Map to hold the updated data
            val updatedData = mapOf(
                "first_name" to updatedFirstName,
                "last_name" to updatedLastName
            )

            // Update the data in Firebase under the specific user ID (replace with your user's ID)
            if (selectedUser != null) {
                val userId = selectedUser.id
                if (userId != null) {
                    databaseReference.child(userId).updateChildren(updatedData)
                        .addOnSuccessListener {
                            // Data updated successfully
                            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            // Handle the error if the update fails
                            Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

    }
}
