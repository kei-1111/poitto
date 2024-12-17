package com.example.flush.data.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.flush.domain.model.ThrowingItem
import com.example.flush.domain.repository.ThrowingItemRepository
import com.example.flush.ktx.toByteArray
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ThrowingItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : ThrowingItemRepository {
    private val throwingItemCollection = firestore.collection("throwing_items")

    override suspend fun getThrowingItems(): Result<List<ThrowingItem>, String> =
        try {
            val snapshot = throwingItemCollection.get().await()
            val throwingItems = snapshot.documents.mapNotNull { document ->
                document.toObject(ThrowingItem::class.java)
            }
            Ok(throwingItems)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get throwing items", e)
            Err(e.message ?: "Unknown error")
        }

    override suspend fun createThrowingItem(throwingItem: ThrowingItem): Result<Unit, String> =
        try {
            throwingItemCollection.document(throwingItem.id).set(throwingItem).await()
            Ok(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create throwing item", e)
            Err(e.message ?: "Unknown error")
        }

    override suspend fun uploadImage(throwingItemId: String, imageUrl: Uri): Result<String, String> =
        try {
            val imageRef = storage.reference.child("throwing_items/$throwingItemId/image.jpg")
            imageRef.putFile(imageUrl).await()
            val result = imageRef.downloadUrl.await()
            Ok(result.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Failed to upload image", e)
            Err(e.message ?: "Unknown error")
        }

    override suspend fun uploadTextureBitmap(throwingItemId: String, textureBitmap: Bitmap): Result<String, String> =
        try {
            val imageRef = storage.reference.child("throwing_items/$throwingItemId/texture.jpg")
            val data = textureBitmap.toByteArray()
            imageRef.putBytes(data).await()
            val result = imageRef.downloadUrl.await()
            Ok(result.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Failed to upload texture bitmap", e)
            Err(e.message ?: "Unknown error")
        }

    companion object {
        private const val TAG = "ThrowingItemRepositoryImpl"
    }
}
