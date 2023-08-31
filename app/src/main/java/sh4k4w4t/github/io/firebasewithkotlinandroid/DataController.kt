package sh4k4w4t.github.io.firebasewithkotlinandroid

class DataController {
    var itemClickInterface: OnItemClick? = null
    var userData: User? = null

    companion object {
        var instance: DataController? = null
        fun getInstance(): DataController? {
            if (instance == null) {
                instance = DataController()
            }
            return instance
        }
    }
}