package au.com.shiftyjelly.pocketcasts.repositories.bookmark

import au.com.shiftyjelly.pocketcasts.analytics.SourceView
import au.com.shiftyjelly.pocketcasts.models.entity.BaseEpisode
import au.com.shiftyjelly.pocketcasts.models.entity.Bookmark
import au.com.shiftyjelly.pocketcasts.models.type.BookmarksSortTypeForPlayer
import au.com.shiftyjelly.pocketcasts.models.type.BookmarksSortTypeForPodcast
import kotlinx.coroutines.flow.Flow

interface BookmarkManager {
    suspend fun add(episode: BaseEpisode, timeSecs: Int, title: String, creationSource: CreationSource): Bookmark
    suspend fun updateTitle(bookmarkUuid: String, title: String)
    suspend fun findBookmark(bookmarkUuid: String): Bookmark?
    suspend fun findByEpisodeTime(episode: BaseEpisode, timeSecs: Int): Bookmark?
    suspend fun findEpisodeBookmarksFlow(
        episode: BaseEpisode,
        sortType: BookmarksSortTypeForPlayer,
    ): Flow<List<Bookmark>>
    fun findPodcastBookmarksFlow(
        podcastUuid: String,
        sortType: BookmarksSortTypeForPodcast,
    ): Flow<List<Bookmark>>
    suspend fun deleteToSync(bookmarkUuid: String)
    suspend fun deleteSynced(bookmarkUuid: String)
    suspend fun upsertSynced(bookmark: Bookmark): Bookmark
    fun findBookmarksToSync(): List<Bookmark>
    suspend fun searchInPodcastByTitle(podcastUuid: String, title: String): List<String>
    fun findUserEpisodesBookmarksFlow(): Flow<List<Bookmark>>
    fun findBookmarksFlow(): Flow<List<Bookmark>>

    var sourceView: SourceView

    enum class CreationSource(val analyticsValue: String) {
        HEADPHONES("headphones"),
        PLAYER("player"),
    }
}
