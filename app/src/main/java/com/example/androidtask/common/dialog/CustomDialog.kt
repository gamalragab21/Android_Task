package com.example.androidtask.common.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextParams
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.RequestManager
import com.example.androidtask.R
import com.example.androidtask.common.AppStrings
import com.example.androidtask.databinding.LogoutDialogLayoutBinding
import com.example.androidtask.databinding.ShowProductDetailsDialogeBinding
import com.example.androidtask.domain.mappers.Product


object CustomDialog {

    fun showDialogDetails(
        context: Context,
        product:Product,
        glide:RequestManager
    ) {
        val dialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(context)
        val dialog: Dialog
        val bind: ShowProductDetailsDialogeBinding =
            ShowProductDetailsDialogeBinding.inflate(LayoutInflater.from(context))


        if (bind.root != null) {
            (bind.root as ViewGroup).removeView(bind.root)
        }
        dialogBuilder
            .setView(bind.root)
            .setCancelable(true)
        dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation

        dialog.show()

        glide.load(product.images[0]).into(bind.productImg)
        bind.productDiscountTv.text="${product.discountPercentage}%"
        bind.productPrice.text="LE ${product.price}"
        bind.productRating.text="${product.rating}"
        bind.textDescripition.text=product.description
        bind.productNameTv.text=product.title
        bind.textReadMore.setOnClickListener {
            val textRedMoreValue = bind.textReadMore.text.toString()
            if (textRedMoreValue == context.getString(AppStrings.read_more)) {
                bind.textDescripition.maxLines = Int.MAX_VALUE
                bind.textDescripition.ellipsize = null
                bind.textReadMore.text = context.getString(AppStrings.read_less)
            } else {
                bind.textDescripition.maxLines = 2
                bind.textDescripition.ellipsize = TextUtils.TruncateAt.END
                bind.textReadMore.text = context.getString(AppStrings.read_more)
            }
        }
        bind.backIcon.setOnClickListener {
            // no button id = -1
            dialog.dismiss()
        }
    }



    fun showDialogForLogout(
        context: Context,
        logoutClickListener: (Boolean) -> Unit,
    ) {
        val dialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(context)
        val dialog: Dialog
        val bind: LogoutDialogLayoutBinding =
            LogoutDialogLayoutBinding.inflate(LayoutInflater.from(context))

        if (bind.root != null) {
            (bind.root as ViewGroup).removeView(bind.root)
        }
        dialogBuilder
            .setView(bind.root)
            .setCancelable(true)
        dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog!!.window!!.attributes.windowAnimations = R.style.DialogAnimation

        dialog.show()

        bind.logoutBtnYes.setOnClickListener {
            // no button id = -1
            dialog.dismiss()
            logoutClickListener(true)
        }
        bind.logoutCancelBtn.setOnClickListener {
            // no button id = -1
            logoutClickListener(false)
            dialog.dismiss()
        }
    }


}