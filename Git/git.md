## Git

#### Git文件的状态

![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/Git/region.jpg?raw=true)

| 未加入Git                                     | 工作区                     | 暂存区（.git/index）                                         | 本地仓库（.git/文件目录）                                    |
| --------------------------------------------- | -------------------------- | :----------------------------------------------------------- | ------------------------------------------------------------ |
| 未跟踪或者被.gitignore忽略的文件，未被Git管理 | 修改过的文件，显示在工作区 | 修改过的文件暂存（git add）之后，会被保存到暂存区，暂存区的文件，会形成快照，为提交做准备 | 暂存文件提交（git commit）之后，会保存到本地仓库，并形成 commit 信息，包括：完整快照、Hash值、时间、提交人信息、log等 |
| 未跟踪的文件通常显示为红色                    | 工作区的文件通常显示为红色 | 暂存区的文件通常显示成绿色                                   |                                                              |
| 未跟踪（Untracked）                           | 已修改（Modified）         | 已暂存（Staged）                                             | 已提交（committed）                                          |

#### 本地仓库与远程仓库

![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/Git/gitorder.jpg?raw=true)

#### 分支（Branch）

* 分支是 Git 中最重要的概念，通常每个仓库会有个 master 分支

  * 分支可以让工作并行，不同分支的工作不会相互影响
  * 两个分支合并时，可能会遇到冲突，解决之后才能完成合并

* 分支的查看与切换

  ```shell
  # 查看分支
  git branch
  git branch -a # 查看所有分支 
  git branch -r # 查看远程仓库分支情况
  
  # 切换分支，切换到 master 分支
  git checkout master
  ```

* 分支的创建

  ```shell
  # 创建分支并切换到该分支
  git checkout -b dev_bran
  
  # 基于当前分支创建新分支
  git branch dev01
  ```

* 分支的合并

  ```shell
  # 当前在 dev01 分支，将 dev01 和 dev02 分支进行合并
  # 此时会创建新的节点，将 dev01 和 dev02 进行合并为新的节点
  git merge dev02
  
  # 当前在 dev02 分支，rebase dev01 分支
  git rebase dev01 # 此时会将 dev02 的改动以延续节点的形式合并到 dev01 分支，dev02 分支就结束了，dev01 分支延续
  git merge dev02 # 此时将 dev01 和 dev02 合并为同一个节点
  ```

#### 冲突的解决

```text
<<<<< HEAD       -----------
oooooo            本分支的内容
pppppp  
======           -----------
xxxx             dev01 分支的内容
1111
>>>>> dev01      ----------

自行决定如何处理 
```

#### Git Workflow

* 经典分支模型

  | 分支    | 说明                                                         | 命名规范  | checkout from          | merge to          |
  | ------- | ------------------------------------------------------------ | --------- | ---------------------- | ----------------- |
  | master  | 主干，最稳定的分支，随时可以当作 release 版本，只能从其他分支合入，不能在上面做任何提交 |           |                        |                   |
  | develop | 开发主干，是稳定的、最新的分支，主要合并其他分支，比如 fearute 分支、bugfix 分支。 |           |                        |                   |
  | feature | 新功能分支                                                   | feature/* | develop                | develop           |
  | release | 发布分支，对应一次新版本的发布，只接受 bugfix                | release/* | develop                | develop 和 master |
  | hotfix  | 紧急修复分支，生产环境中发现的紧急 bug 的修复                | hotfix/*  | master（tag）、release | develop和master   |

#### 典型场景

* 多人协作

  ```shell
  # 当本地提交到 master 发现出现冲突时，有以下解决方案
  git fetch # 将远程分支拉下来，但不合并
  git merge o/master # 合并远程分支
  
  # 继续提交即可
  ```

* 回滚

  | 方法                       | 描述                                                         |
  | -------------------------- | ------------------------------------------------------------ |
  | 恢复到某一次提交 git reset | 将该次提交之后的所有提交都丢弃                               |
  | 恢复某一次提交 git revert | 将某一次提交的修改恢复回去                                   |
  | git reflog                 | 用 git reflog 可以查看所有 git 相关的操作记录，并有对应的Hash值，有了 Hash 值，可以搭配 git reset 恢复到任何地方 |

* 重写历史

  ```shell
  git commit -amen  # 修改最后一次提交
  
  git rebase -i     # 1.修改多个提交信息
  			      # 2.重排提交的顺序
  			      # 3.压缩提交
  			      # 4.拆分提交
  ```

#### 常用场景

* 新建远程分支并与本地分支关联

  ```shell
  # 新建分支
  git push origin <branch-name>:<branch-name>
  
  # 与本地分支建立关联
  git branch --set-upstream-to=origin/<branch-name> <branch-name>
  
  # 删除远程分支
  git push origin --delete <branch-name>
  ```

* 有人改动同一分支，无法 pull 和 push

  ```shell
  # 暂存本地改动
  git stash
  
  # 拉取分支
  git pull
  
  # 恢复本地改动
  git stash pop
  ```

* 不小心改动了其他分支

  ```shell
  # 暂存修改
  git stash
  
  # 切换到需要改动的分支
  git checkout test
  
  # 将改动恢复到当前要改动的分支
  git stash pop
  ```

  
