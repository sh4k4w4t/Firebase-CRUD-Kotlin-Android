package sh4k4w4t.github.io.firebasewithkotlinandroid

object DataController {
    private var selectedUser: User? = null

    fun setSelectedUser(user: User?) {
        selectedUser = user
    }

    fun getSelectedUser(): User? {
        return selectedUser
    }
}
