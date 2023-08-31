package di

object FavoritesManager {
    private val favorites = mutableListOf<TechItem>()

    fun getFavorites(): List<TechItem> {
        return favorites.toList()
    }

    fun addFavorite(techItem: TechItem) {
        favorites.add(techItem)
    }

    fun removeFavorite(techItem: TechItem) {
        favorites.remove(techItem)
    }
}