INSERT INTO movies(
    title, slug, original_title, release_year, genre, director, cast_members,
    rating, recommendation, watched_at, poster_url, summary, review_body,
    memorable_point, status, amazon_prime_url, netflix_url, disney_plus_url,
    unext_url, hulu_url, abema_url, other_service_name, other_service_url,
    view_count, published_at, created_at, updated_at
)
VALUES
(
    'ショーシャンクの空に',
    'the-shawshank-redemption',
    'The Shawshank Redemption',
    1994,
    'ドラマ',
    'フランク・ダラボン',
    'ティム・ロビンス、モーガン・フリーマン',
    4.8,
    5,
    CURRENT_DATE,
    NULL,
    '希望は、状況ではなく自分の内側に残るものなのかもしれない。',
    'これはサンプル記事です。管理画面から、あなた自身の言葉に置き換えてください。

映画を観ているようで、私たちはときどき自分自身を観ています。

どれだけ状況が変わらなくても、自分の中に残しているものまで奪われるわけではない。そんな静かな強さを感じた作品でした。',
    '自由とは、外に出ることだけではない。',
    'PUBLISHED',
    'https://www.amazon.co.jp/s?k=%E3%82%B7%E3%83%A7%E3%83%BC%E3%82%B7%E3%83%A3%E3%83%B3%E3%82%AF%E3%81%AE%E7%A9%BA%E3%81%AB&i=instant-video',
    'https://www.netflix.com/search?q=%E3%82%B7%E3%83%A7%E3%83%BC%E3%82%B7%E3%83%A3%E3%83%B3%E3%82%AF%E3%81%AE%E7%A9%BA%E3%81%AB',
    NULL,
    'https://video.unext.jp/freeword?query=%E3%82%B7%E3%83%A7%E3%83%BC%E3%82%B7%E3%83%A3%E3%83%B3%E3%82%AF%E3%81%AE%E7%A9%BA%E3%81%AB',
    NULL,
    NULL,
    NULL,
    NULL,
    120,
    NOW(),
    NOW(),
    NOW()
),
(
    'パーフェクト・ブルー',
    'perfect-blue',
    'PERFECT BLUE',
    1997,
    'サスペンス',
    '今敏',
    '岩男潤子、松本梨香',
    4.5,
    5,
    CURRENT_DATE,
    NULL,
    '他人の視線によって作られた自分と、本当の自分の境界。',
    '現実と虚構が静かに崩れていく作品。

「見られている自分」と「自分が思う自分」の境目は、思っているより曖昧なのかもしれません。

これはレイアウト確認用のサンプル本文です。管理画面から自由に編集できます。',
    '自分らしさは、誰が決めるのか。',
    'PUBLISHED',
    'https://www.amazon.co.jp/s?k=%E3%83%91%E3%83%BC%E3%83%95%E3%82%A7%E3%82%AF%E3%83%88%E3%83%BB%E3%83%96%E3%83%AB%E3%83%BC&i=instant-video',
    'https://www.netflix.com/search?q=%E3%83%91%E3%83%BC%E3%83%95%E3%82%A7%E3%82%AF%E3%83%88%E3%83%BB%E3%83%96%E3%83%AB%E3%83%BC',
    NULL,
    'https://video.unext.jp/freeword?query=%E3%83%91%E3%83%BC%E3%83%95%E3%82%A7%E3%82%AF%E3%83%88%E3%83%BB%E3%83%96%E3%83%AB%E3%83%BC',
    NULL,
    NULL,
    NULL,
    NULL,
    88,
    NOW(),
    NOW(),
    NOW()
)
ON CONFLICT (slug) DO UPDATE SET
    amazon_prime_url = EXCLUDED.amazon_prime_url,
    netflix_url = EXCLUDED.netflix_url,
    unext_url = EXCLUDED.unext_url,
    updated_at = NOW();
