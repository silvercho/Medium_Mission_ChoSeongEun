<script>
    // GitHub 사용자 이름을 설정
    const username = 'jhs512';
    // 리포지토리 목록을 저장할 상태
    let repositories = $state([]);
    // 컴포넌트 마운트 시 GitHub API로부터 데이터 가져오기
    $effect(async () => {
        const response = await fetch(`https://api.github.com/users/${username}/repos?per_page=10`);
        repositories = await response.json();
    });
</script>

<svelte:head>
    <title>Home</title>
    <meta name="description" content="홈 화면"/>
</svelte:head>

<h1>{username}'s repositories</h1>
<ul>
    {#each repositories as repo}
        <li>
            {repo.name}
        </li> <!-- 여기서 repo 객체의 다른 속성도 사용할 수 있습니다 -->
    {/each}
</ul>