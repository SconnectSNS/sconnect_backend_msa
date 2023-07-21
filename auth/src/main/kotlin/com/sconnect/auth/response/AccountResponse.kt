package com.sconnect.auth.response

import com.sconnect.auth.model.entity.Account

class AccountResponse(
    val accountId: Long,
    val email: String,
    val nickname: String,
) {
    constructor(account: Account) : this(
        accountId = account.accountId,
        email = account.email,
        nickname = account.nickname,
    )
}
