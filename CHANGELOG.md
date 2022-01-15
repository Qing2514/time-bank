<a name=""></a>

# v0.4.0

*2022-1-10*

### Bug Fixes

* 修复 JPA 无法启动 ([9e35ce8](https://e.coding.net/fortuna/bampo/bampo-spring/commits/9e35ce8))
* **appeal:** 修复无法创建申诉 ([c53a096](https://e.coding.net/fortuna/bampo/bampo-spring/commits/c53a096))
* **article:** 修复文章摘要详情未包装错误 ([b3ab53d](https://e.coding.net/fortuna/bampo/bampo-spring/commits/b3ab53d))
* **contract:** 修复 credential 文件无法读取 ([c464e2b](https://e.coding.net/fortuna/bampo/bampo-spring/commits/c464e2b))
* **team:** 修复无法加入团队 ([f71ebdc](https://e.coding.net/fortuna/bampo/bampo-spring/commits/f71ebdc))
* **transaction:** 修复创建和修改交易信息的接口 ([09e8c98](https://e.coding.net/fortuna/bampo/bampo-spring/commits/09e8c98))
* **transaction:** 修复活动结束前未传入所有交易信息 ([1d3ebb2](https://e.coding.net/fortuna/bampo/bampo-spring/commits/1d3ebb2))
* **util:** 修复截取详情摘要长度错误 ([d408068](https://e.coding.net/fortuna/bampo/bampo-spring/commits/d408068))
* **util:** 修复搜索字符串时 `keywordIndex` 为 -1 ([43c57b5](https://e.coding.net/fortuna/bampo/bampo-spring/commits/43c57b5))
* **voting:** 修复候选者可以给自己投票 ([9b413e3](https://e.coding.net/fortuna/bampo/bampo-spring/commits/9b413e3))

### Features

* 添加申诉详情接口 ([2614332](https://e.coding.net/fortuna/bampo/bampo-spring/commits/2614332))
* 添加投票的功能 ([4172a52](https://e.coding.net/fortuna/bampo/bampo-spring/commits/4172a52))
* 添加验证和新建响应的包装 ([3b440ee](https://e.coding.net/fortuna/bampo/bampo-spring/commits/3b440ee))
* 添加与区块链交互的函数声明 ([aaed752](https://e.coding.net/fortuna/bampo/bampo-spring/commits/aaed752))
* 完成处理活动审查结果的接口 ([6891a88](https://e.coding.net/fortuna/bampo/bampo-spring/commits/6891a88))
* 完成处理申诉结果的接口 ([b4965ae](https://e.coding.net/fortuna/bampo/bampo-spring/commits/b4965ae))
* 完成审核人更替的接口 ([728ea43](https://e.coding.net/fortuna/bampo/bampo-spring/commits/728ea43))
* **activity:** 添加发起人筛选志愿者功能 ([d385835](https://e.coding.net/fortuna/bampo/bampo-spring/commits/d385835))
* **activity:** 添加活动与区块链间的接口 ([98ce6c6](https://e.coding.net/fortuna/bampo/bampo-spring/commits/98ce6c6))
* **activity:** 完成活动部分所需要的接口 ([fd968ee](https://e.coding.net/fortuna/bampo/bampo-spring/commits/fd968ee))
* **activity:** 完成审核人审核活动功能 ([511ce83](https://e.coding.net/fortuna/bampo/bampo-spring/commits/511ce83))
* **appeal:** 添加申诉创建和查看的接口 ([1fb5815](https://e.coding.net/fortuna/bampo/bampo-spring/commits/1fb5815))
* **appeal:** 添加申诉列表包装 ([ed37512](https://e.coding.net/fortuna/bampo/bampo-spring/commits/ed37512))
* **contract:** 更新合约类 ([123b556](https://e.coding.net/fortuna/bampo/bampo-spring/commits/123b556))
* **contract:** 添加合约包装类 ([87a5006](https://e.coding.net/fortuna/bampo/bampo-spring/commits/87a5006))
* **contract:** 添加合约类 ([a1f59d5](https://e.coding.net/fortuna/bampo/bampo-spring/commits/a1f59d5))
* **contract:** 完善合约包装类 ([3c29e75](https://e.coding.net/fortuna/bampo/bampo-spring/commits/3c29e75))
* **model:** 添加搜索响应的包装 ([aaa9932](https://e.coding.net/fortuna/bampo/bampo-spring/commits/aaa9932))
* **model:** 添加详情响应的包装 ([420a762](https://e.coding.net/fortuna/bampo/bampo-spring/commits/420a762))
* **model:** 添加摘要响应的包装 ([292f807](https://e.coding.net/fortuna/bampo/bampo-spring/commits/292f807))
* **model:** 添加注册和登录 HTTP 响应的包装 ([efc7aa2](https://e.coding.net/fortuna/bampo/bampo-spring/commits/efc7aa2))
* **transaction:** 完成交易的增删改查功能 ([2cea17d](https://e.coding.net/fortuna/bampo/bampo-spring/commits/2cea17d))
* **transaction:** 完善交易的查询功能 ([4d1f725](https://e.coding.net/fortuna/bampo/bampo-spring/commits/4d1f725))
* **transaction:** 修改交易创建接口实现 ([96d732f](https://e.coding.net/fortuna/bampo/bampo-spring/commits/96d732f))
* **voting:** 添加投票、成为候选人、查询票数的接口 ([b3814f9](https://e.coding.net/fortuna/bampo/bampo-spring/commits/b3814f9))

### Performance Improvements

* **appeal:** 优化申诉排序方式 ([f6edd96](https://e.coding.net/fortuna/bampo/bampo-spring/commits/f6edd96))

# v0.3.0

*2021-12-30*

### Bug Fixes

* 修复 UUID 的 Base64 编码和解码错误 ([1b834e7](https://e.coding.net/fortuna/bampo/bampo-spring/commits/1b834e7))
* 修复查看活动信息接口 ([169b6df](https://e.coding.net/fortuna/bampo/bampo-spring/commits/169b6df))
* 修复团队创建字段缺失 ([3537fe9](https://e.coding.net/fortuna/bampo/bampo-spring/commits/3537fe9))
* 优化查询接口 ([c2f733b](https://e.coding.net/fortuna/bampo/bampo-spring/commits/c2f733b))
* **activity:** 修复活动删除接口 ([6d4990a](https://e.coding.net/fortuna/bampo/bampo-spring/commits/6d4990a))
* **activity:** 修复活动修改不成功 ([5ad6a96](https://e.coding.net/fortuna/bampo/bampo-spring/commits/5ad6a96))
* **article:** 对文章修改、查询的优化 ([47ef5a3](https://e.coding.net/fortuna/bampo/bampo-spring/commits/47ef5a3))
* **article:** 优化文章查询接口 ([8df37e8](https://e.coding.net/fortuna/bampo/bampo-spring/commits/8df37e8))
* **article:** 优化文章增删改接口 ([09646aa](https://e.coding.net/fortuna/bampo/bampo-spring/commits/09646aa))
* **controller:** 删除打印行 ([de1a8f4](https://e.coding.net/fortuna/bampo/bampo-spring/commits/de1a8f4))
* **controller:** 修改删改团队接口传递的参数 ([1a0eb4f](https://e.coding.net/fortuna/bampo/bampo-spring/commits/1a0eb4f))
* **controller:** 修改团队修改接口的路径和方法名 ([a373199](https://e.coding.net/fortuna/bampo/bampo-spring/commits/a373199))
* **model:** 添加修改返回 username 字段 ([2655a16](https://e.coding.net/fortuna/bampo/bampo-spring/commits/2655a16))
* **team:** 修复团队查找、更新和删除 ([7cf9b49](https://e.coding.net/fortuna/bampo/bampo-spring/commits/7cf9b49))
* **user:** 完善修改用户接口 ([1072f91](https://e.coding.net/fortuna/bampo/bampo-spring/commits/1072f91))

### Features

* 添加获取用户详细信息接口 ([863b0ec](https://e.coding.net/fortuna/bampo/bampo-spring/commits/863b0ec))
* 修改搜索返回类型为活动 id 列表 ([ef6a55f](https://e.coding.net/fortuna/bampo/bampo-spring/commits/ef6a55f))
* **activity:** 添加活动附加描述接口 ([d349737](https://e.coding.net/fortuna/bampo/bampo-spring/commits/d349737))
* **activity:** 添加活动删除接口 ([0a8c138](https://e.coding.net/fortuna/bampo/bampo-spring/commits/0a8c138))
* **activity:** 添加修改活动的接口 ([2e3d7ef](https://e.coding.net/fortuna/bampo/bampo-spring/commits/2e3d7ef))
* **article:** 完成文章的增删改查功能 ([d9a4c58](https://e.coding.net/fortuna/bampo/bampo-spring/commits/d9a4c58))
* **team:** 添加删除和修改团队接口 ([5d9ae14](https://e.coding.net/fortuna/bampo/bampo-spring/commits/5d9ae14))
* **team:** 添加团队删除接口 ([f50dbf9](https://e.coding.net/fortuna/bampo/bampo-spring/commits/f50dbf9))
* **team:** 添加团队转让和退出接口 ([bc182c8](https://e.coding.net/fortuna/bampo/bampo-spring/commits/bc182c8))
* **team:** 添加修改团队基本信息接口 ([b19e46b](https://e.coding.net/fortuna/bampo/bampo-spring/commits/b19e46b))
* **user:** 添加团队成员退出团队接口 ([f233f46](https://e.coding.net/fortuna/bampo/bampo-spring/commits/f233f46))
* **user:** 添加修改用户基本信息接口 ([bd44f2b](https://e.coding.net/fortuna/bampo/bampo-spring/commits/bd44f2b))
* **user:** 添加用户加入团队接口 ([aabd181](https://e.coding.net/fortuna/bampo/bampo-spring/commits/aabd181))
* **user:** 添加用户修改用户名和密码接口 ([768be25](https://e.coding.net/fortuna/bampo/bampo-spring/commits/768be25))
* **user:** 添加用户注册后邮箱验证功能 ([fd6fcec](https://e.coding.net/fortuna/bampo/bampo-spring/commits/fd6fcec))
* **user:** 添加邮件发送接口 ([d0bcf8c](https://e.coding.net/fortuna/bampo/bampo-spring/commits/d0bcf8c))

# v0.2.0

*2021-12-22*

### Bug Fixes

* 修复 DTO 接收不到数据 ([a93aa01](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/a93aa01))
* 修复常量使用 `Integer` 包装类的问题 ([9259056](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/9259056))
* 修复搜索语句错误 ([01168ce](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/01168ce))

### Features

* 数据传输时使用 Base64 加密 id ([bdb708b](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/bdb708b))
* 搜索结果默认综合排序 ([dbc2500](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/dbc2500))
* 添加字符串分段模糊匹配支持 ([03e29f1](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/03e29f1))
* 完成团队的搜索和筛选 ([231579b](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/231579b))
* **activity:** 添加多状态筛选搜索结果 ([1c47b40](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/1c47b40))
* **activity:** 添加通过服务类型和发起人筛选搜索结果 ([b6a3ed1](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/b6a3ed1))
* **activity:** 添加通过活动状态筛选搜索结果 ([5ada796](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/5ada796))
* **activity:** 增加搜索分页和排序功能并规范返回数据 ([8b4de52](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/8b4de52))
* **activity:** 添加活动搜索接口 ([dd2698e](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/dd2698e))
  ([3af4a6c](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/3af4a6c))
  ([bdaff3d](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/bdaff3d))
  ([c1dff11](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/c1dff11))
  ([3d18b15](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/3d18b15))
* **team:** 添加通过创建者筛选搜索结果 ([8707aa8](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/8707aa8))
* **user:** 添加通过城市和账户状态筛选搜索结果 ([3ed9951](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/3ed9951))
* **user:** 添加通过关键字搜索团队 ([4940d31](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/4940d31))
* **user:** 添加通过用户角色筛选搜索结果 ([2b204e3](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/2b204e3))
* **user:** 完成用户搜索的后端返回 ([71dccc4](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/71dccc4))

# v0.1.0

*2021-12-14*

### Bug Fixes

* **entity:** 修复活动 id 生成策略 ([acb8b24](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/acb8b24))
* **filter:** 修复登录 API 路径错误 ([62be268](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/62be268))
* **service:** 添加团队名称非空验证 ([f961643](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/f961643))
* **team:** 修复 Team 注册 API ([ba8b3c6](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/ba8b3c6))

### Features

* 添加用户注册和登录功能 ([d912349](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/d912349))
* 完成团队的创建和查看的接口 ([7fa866c](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/7fa866c))
* 用户注册时验证身份信息是否已存在 ([d18bc8f](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/d18bc8f))
* 完成活动创建、查询和加入的接口 ([89a4b4c](https://fortuna.coding.net/p/bampo/d/bampo-spring/git/commit/89a4b4c))



