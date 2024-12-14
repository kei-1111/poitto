package com.example.flush.data.repository

import android.net.Uri
import android.util.Log
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.repository.ThrowingItemRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ThrowingItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ThrowingItemRepository {
    private val throwingItemCollection = firestore.collection("throwingItems")

    override suspend fun getThrowingItems(): Result<List<ThrowingItem>> =
        withContext(ioDispatcher) {
            try {
                val snapshot = throwingItemCollection.get().await()
                val throwingItems = snapshot.documents.mapNotNull { document ->
                    document.toObject(ThrowingItem::class.java)
                }
                Result.success(throwingItems)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun createThrowingItem(throwingItem: ThrowingItem): Result<Unit> =
        withContext(ioDispatcher) {
            try {
                throwingItemCollection.document(throwingItem.id).set(throwingItem).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun uploadImage(throwingItemId: String, imageUrl: Uri): Result<String> =
        withContext(ioDispatcher) {
            try {
                val imageRef = storage.reference.child("throwing_items/$throwingItemId/image.jpg")
                imageRef.putFile(imageUrl).await()
                val result = imageRef.downloadUrl.await()
                Result.success(result.toString())
            } catch (e: Exception) {
                Log.e(TAG, "Failed to upload image", e)
                Result.failure(e)
            }
        }

    companion object {
        private const val TAG = "ThrowingItemRepositoryImpl"
    }
}
