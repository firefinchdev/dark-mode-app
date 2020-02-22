package com.softinit.darkmode

import android.content.Context
import android.widget.Toast
import com.codemybrainsout.ratingdialog.RatingDialog
import com.softinit.darkmode.AppConstants.APP_RATE_DIALOG_INTERVAL

object AppRatingDialog {

    fun getDialog(context: Context, session: Boolean): RatingDialog {
        return getDialog(
            context,
            if (session) APP_RATE_DIALOG_INTERVAL else 1
        )
    }

    fun getDialog(context: Context, sessionCount: Int): RatingDialog {
        var userRating = 0F
        return RatingDialog.Builder(context)
            .icon(context.getDrawable(R.mipmap.ic_launcher))
            .session(sessionCount)
            .threshold(4F)
            .title(context.getString(R.string.rate_app))
            .positiveButtonText(context.getString(R.string.rate_later))
            .negativeButtonText(context.getString(R.string.rate_never))
            .positiveButtonTextColor(R.color.colorPrimary)
            .negativeButtonTextColor(R.color.grey_400)
            .ratingBarColor(R.color.colorPrimary)
            .ratingBarBackgroundColor(R.color.grey_200)
            .playstoreUrl("http://play.google.com/store/apps/details?id=${context.packageName}")
            .onRatingChanged { rating, thresholdCleared ->
                userRating = rating
                if (thresholdCleared) {
                    Toast.makeText(context,context.getString(R.string.rateUs),Toast.LENGTH_LONG).show()
                }
            }
            .onRatingBarFormSumbit { feedback ->
                Toast.makeText(context,context.getString(R.string.send_feedback),Toast.LENGTH_LONG).show()
                Utils.sendFeedback(context, "$feedback\n\nRating: $userRating")
            }
            .build()
    }
}